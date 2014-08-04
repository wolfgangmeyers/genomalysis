package org.genomalysis.tmprediction;

public class TMAP {

	public static final String UTGAVA = "53";
	public static final int STD_NUMBER = 40;
	public static final int MSF_NUMBER = 40;
	public static final int TM_NUMBER = 100;
	public static final int TM_SEQUENCES = 3000;
	/** Max antal profiler */
	public static final int MAX_PROF = 10;
	/** Max antal pred. TM-segment */
	public static final int MAXHIT = 5000; 
	public static final int N_SPANN = 4;
	public static final int M_SPANN = 21;
	public static final int C_SPANN = 4;
	public static final int N_FORLANGNING = 10;
	public static final int C_FORLANGNING = 10;
	public static final int N_FOSFAT = 4;
	public static final int C_FOSFAT = 4;
	public static final int FORLC = 8;
	public static final int FORLN = 8;
	public static final int START_E_KORR = 2;
	public static final int STOPP_E_KORR = 2;
	public static final int M_KORT2_LIMIT = 8;
	public static final int M_KORT3_LIMIT = 16;
	public static final int M_LANG1_LIMIT = 29;
	/**
	 * Proportion of sequences that should be present at a position in order to
	 * utilise the data from that position.
	 */
	public static final double ALI_MINIMUM = 0.30;
	public static final int E_SPANN_MIN = 20;
	public static final int E_SPANN_MAX = 33;
	public static final int E_STST_DIFF = 6;
	public static final String GAP = ".";
	
	public static final double NOLLVARDE = -10.000;
	
	public static final int NUMBER = 10000;
	public static final int LENGTH = 10000;
	
	private double[][] P = new double[][] {
		/* Pm values */
		new double[] {
			1.409446,
			0.000000,
			1.068500,
			0.192356,
			0.174588,
			1.965858,
			1.058479,
			0.587963,
			1.990336,
			0.000000,
			0.180983,
			1.701726,
			1.500664,
			0.433590,
			0.000000,
			0.518571,
			0.344232,
			0.239737,
			0.774442,
			0.828131,
			0.000000,
			1.694256,
			1.314157,
			0.000000,
			0.979187,
			0.000000
		},
		/* Pe values */
		new double[] {
			0.887866,
			0.000000,
			0.842097,
			0.739931,
			0.804004,
			1.102175,
			0.919923,
			1.117477,
			1.103394,
			0.000000,
			1.178047,
			0.997766,
			1.171823,
			1.103455,
			0.000000,
			0.881061,
			0.889218,
			1.519044,
			0.919717,
			0.881105,
			0.000000,
			0.869741,
			1.450220,
			0.000000,
			1.314105,
			0.000000
		}
	};
	
	private int xml_output = 0;
	
	
}
