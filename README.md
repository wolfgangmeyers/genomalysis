genomalysis
===========

Summary

Genomalysis is a java application that allows users to perform data mining operations and analysis on the proteomes of various species. In addition to providing a rich graphical user interface, Genomalysis provides a platform on which end users can develop their own instruments to mine and analyze protein sequences. Genomalysis can open and parse proteome files in the FASTA file format. By providing an extensible mechanism to do bulk processing on protein sequences, Genomalysis has the potential to speed up research efforts by orders of magnitude.

Data mining

In Genomalysis, data mining consists of loading a source FASTA file, selecting and configuring a set of protein sequence filters that will be applied to the protein sequences, and writing the result to a destination FASTA file. Protein sequences are tested on each sequence filter in turn, after which the filter will return a pass/fail response. Protein sequences that receive a pass response are sent to the next filter in the set. If a protein sequence receives a pass response from every filter in the set, the protein sequence is written to the output file.
In addition to providing a set of protein sequence filters out of the box, Genomalysis provides a mechanism to extend this functionality, allowing end-users to define and distribute their own implementations of the protein sequence filter interface that can be used in the data mining process. A flexible configuration system has been provided that allows protein sequence filters to declare what kind of user interface should be used to set parameters that are used during the data mining process.

Here is a list of protein sequence filters that are currently implemented in Genomalysis:

Transmembrane Prediction Filter: This filter tests for predicted transmembrane segments using the TMAP algorithm. The filter can be configured to only pass protein sequences that have a minimum/maximum number of transmembrane segments.

Clevage Site Filter: This filter tests for predicted cleavage sites of proteins. Currently it uses SignalP to test protein sequences.

Regex Filter: This filter allows the user to apply regular expressions to test protein sequences.

Sequence Length Filter: This filter tests protein sequences based on the number of amino acids. Protein sequences pass the test only if they are above the minimum length and below the maximum length.

AND filter: This filter is a logical combination filter. Other filters can be added as “children”, and protein sequences will pass the test only if all child filters pass.

OR filter: This filter is a logical combination filter. Other filters can be added as “children”, and protein sequences will pass the test if at least one child filter passes.

Data Analysis

	Data Analysis is a more recent addition to Genomalysis. The diagnostic tool interface provides a way to process sets of protein sequences and output diagnostic results in text and graphical format.

Here is a list of diagnostic tools that are provided out of the box by Genomalysis:

ClustalW tool:

Hydropathy Plot Generator:

Implementation

	Genomalysis is implemented in Java, although some components may integrate with other tools that are implemented in different languages. The user interface is implemented in using Swing, and uses an event-based model to interact with the core components of the system. User interface elements include the main window (which shows up when you launch the application) and various dialogs. Genomalysis can be extended by classes that implement the IProteinSequenceFilter interface or the IProteinDiagnosticsTool interface. A plugin framework is used to read the contents of jar files and discover classes that implement these interfaces - once discovered, they will be displayed in the user interface and can be used to mine and analyze protein sequences. Users can also make use of the “sequence cache” - this provides the ability to hand-pick different sequences, group them together, and save/load these groups to/from FASTA files. This serves as a sort of crude clipboard functionality, and may be improved in the future.

Building

Currently somewhat of a mess, but here is the current process:

from the root of the project, run: sbt stage package
The main application is built at Genomalysis/target/universal/stage/bin/genomalysis.bat
After running the build, create a "plugins" directory in Genomalysis/target/universal/stage/bin/ and copy the following into it:
ClustalWTool/target/scala-2.10/clustalw-tool_2.10-1.0.jar
HydropathyPlot/target/scala-2.10/hydropathy-plots_2.10-1.0.jar
StandardFilters/target/scala-2.10/standard-filters_2.10-1.0.jar
