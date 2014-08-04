/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.genomalysis.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.genomalysis.control.filters.CountingFilter;
import org.genomalysis.control.filters.PauseFilter;
import org.genomalysis.plugin.PluginInstance;
import org.genomalysis.proteintools.IProteinSequenceFilter;
import org.genomalysis.proteintools.ISequenceIO;
import org.genomalysis.proteintools.InitializationException;
import org.genomalysis.proteintools.ProteinSequence;
import org.genomalysis.proteintools.SequenceIOImpl;
import org.genomalysis.proteintools.SequenceTransformer;

/**
 *
 * @author ameyers
 */
public class FilterControl implements IObservable {
	private CountingFilter readFilter = new CountingFilter();
	private CountingFilter writeFilter = new CountingFilter();
	private PauseFilter pauseFilter = new PauseFilter();
	private FilterProgressControl progressControl = new FilterProgressControl();
	private File inputFile;
	private File outputFile;
	private List<PluginInstance<IProteinSequenceFilter>> filterInstances = new ArrayList<PluginInstance<IProteinSequenceFilter>>();
	private boolean running = false;
	private SequenceTransformer xformer = new SequenceTransformer();
	private Thread backgroundThread = null;
	private ISequenceIO sequenceIO = SequenceIOImpl.getDefaultIO();
	
	private EventSupport eventSupport = new EventSupport();

	public FilterControl() {
		progressControl.setReadFilter(readFilter);
		progressControl.setWriteFilter(writeFilter);
	}

	public void start(IProcessCompleteCallback callback)
			throws InitializationException, IOException {
		this.progressControl.reset();
		final IProcessCompleteCallback processCallback = callback;
		final List<IProteinSequenceFilter> filters = new ArrayList<IProteinSequenceFilter>();
		filters.add(readFilter);
		filters.add(pauseFilter);
		// if any intialization errors occur, they should happen here
		for (PluginInstance<IProteinSequenceFilter> instance : filterInstances) {
			instance.getPluginInstance().initialize();
			filters.add(instance.getPluginInstance());
		}
		filters.add(writeFilter);
		if (inputFile == null) {
			throw new IOException("Please specify an input file");
		}
		if (!inputFile.exists()) {
			throw new FileNotFoundException(inputFile.getName()
					+ " cannot be found");
		}
		if (outputFile == null) {
			throw new IOException("Please specify an output file");
		}

		// reset the read/write counters
		readFilter.initialize();
		writeFilter.initialize();

		// we need to get a total count of the sequences in the file
		FileInputStream fin = new FileInputStream(inputFile);
		Iterator<ProteinSequence> sequenceIterator = sequenceIO
				.readSequences(fin);
		int counter = 0;
		while (sequenceIterator.hasNext()) {
			sequenceIterator.next();
			counter++;
		}
		progressControl.setTotalCount(counter);

		int updateInterval = (int) Math.ceil(counter / 1000);
		if (updateInterval < 1)
			updateInterval = 1;
		this.readFilter.setCountingInterval(updateInterval);
		this.running = true;
		eventSupport.notifyObservers();
		backgroundThread = new Thread(new Runnable() {

			public void run() {
				try {
					System.out
							.println("FilterControl: attempting to filter input file "
									+ inputFile.getName()
									+ " into file "
									+ outputFile.getName());
					xformer.transformSequenceFile(inputFile, outputFile,
							filters);
					running = false;
					if (processCallback != null)
						processCallback.processCompleted();
				} catch (Exception ex) {
					running = false;
					Logger.getLogger(FilterControl.class.getName()).log(
							Level.SEVERE, null, ex);
					eventSupport.notifyObservers();
					eventSupport.notifyObserversOfError(ex.getClass().getSimpleName() + ": "
							+ ex.getMessage());
				}
			}
		});
		backgroundThread.start();
	}

	public void stop() {
		if (backgroundThread != null) {
			try {
				System.out.println("FilterControl: Stopping Filter Process");

				this.running = false;

				System.out.println("FilterControl: Stopping Transformer");
				xformer.cancelTransform();
				// 10 second timeout, and we force termination
				Thread.sleep(500);
				if (backgroundThread.getState() != Thread.State.WAITING) {
					Thread.sleep(8000);
					if (backgroundThread.getState() != Thread.State.WAITING) {
						backgroundThread.interrupt();
					}
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(FilterControl.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}
		eventSupport.notifyObservers();
	}

	public boolean isPaused() {
		return pauseFilter.isPaused();
	}

	public void togglePause() {
		if (pauseFilter.isPaused())
			pauseFilter.UnPause();
		else
			pauseFilter.Pause();
		eventSupport.notifyObservers();
	}

	public File getInputFile() {
		return inputFile;
	}

	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
		eventSupport.notifyObservers();
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
		eventSupport.notifyObservers();
	}

	public boolean isRunning() {
		return running;
	}

	public FilterProgressControl getProgressControl() {
		return progressControl;
	}

	public List<PluginInstance<IProteinSequenceFilter>> getFilterInstances() {
		return filterInstances;
	}

	public void setFilterInstances(
			List<PluginInstance<IProteinSequenceFilter>> filterInstances) {
		this.filterInstances = filterInstances;
	}

	@Override
	public void addObserver(IObserver observer) {
		eventSupport.addObserver(observer);
	}

	@Override
	public void removeObserver(IObserver observer) {
		eventSupport.addObserver(observer);
	}

}
