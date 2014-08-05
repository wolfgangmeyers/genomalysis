package org.genomalysis.tmprediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

public class TMAP {

    private static final String UTGAVA = "53";
    private static final int STD_NUMBER = 40;
    private static final int MSF_NUMBER = 40;
    private static final int TM_NUMBER = 100;
    private static final int TM_SEQUENCES = 3000;
    /** Max antal profiler */
    private static final int MAX_PROF = 10;
    /** Max antal pred. TM-segment */
    private static final int MAXHIT = 5000;
    private static final int N_SPANN = 4;
    private static final int M_SPANN = 21;
    private static final int C_SPANN = 4;
    private static final int N_FORLANGNING = 10;
    private static final int C_FORLANGNING = 10;
    private static final int N_FOSFAT = 4;
    private static final int C_FOSFAT = 4;
    private static final int FORLC = 8;
    private static final int FORLN = 8;
    private static final int START_E_KORR = 2;
    private static final int STOPP_E_KORR = 2;
    private static final int M_KORT2_LIMIT = 8;
    private static final int M_KORT3_LIMIT = 16;
    private static final int M_LANG1_LIMIT = 29;
    private static final int CONSIDER = 40;
    /**
     * Proportion of sequences that should be present at a position in order to
     * utilise the data from that position.
     */
    private static final double ALI_MINIMUM = 0.30;
    private static final int E_SPANN_MIN = 20;
    private static final int E_SPANN_MAX = 33;
    private static final int E_STST_DIFF = 6;
    private static final char GAP = '.';

    private static final double NOLLVARDE = -10.000;

    private static final int NUMBER = 10000;
    private static final int LENGTH = 10000;

    private double[][] P = new double[][] {
            /* Pm values */
            new double[] { 1.409446, 0.000000, 1.068500, 0.192356, 0.174588,
                    1.965858, 1.058479, 0.587963, 1.990336, 0.000000, 0.180983,
                    1.701726, 1.500664, 0.433590, 0.000000, 0.518571, 0.344232,
                    0.239737, 0.774442, 0.828131, 0.000000, 1.694256, 1.314157,
                    0.000000, 0.979187, 0.000000 },
            /* Pe values */
            new double[] { 0.887866, 0.000000, 0.842097, 0.739931, 0.804004,
                    1.102175, 0.919923, 1.117477, 1.103394, 0.000000, 1.178047,
                    0.997766, 1.171823, 1.103455, 0.000000, 0.881061, 0.889218,
                    1.519044, 0.919717, 0.881105, 0.000000, 0.869741, 1.450220,
                    0.000000, 1.314105, 0.000000 } };

    private int xml_output = 0;

    private int[][] linjetyp = new int[][] { new int[] { 0, 0 },
            new int[] { 3, 3 }, new int[] { 1, 2 }, new int[] { 5, 1 },
            new int[] { 7, 2 }, new int[] { 1, 1 }, new int[] { 0, 0 },
            new int[] { 0, 0 }, new int[] { 0, 0 }, new int[] { 0, 0 } };

    private String[] stand_namn = new String[STD_NUMBER];
    private String test_namn;
    private int[][] stand_start = new int[STD_NUMBER][TM_NUMBER];
    private int[][] stand_stopp = new int[STD_NUMBER][TM_NUMBER];
    private int[] corr_standard = new int[TM_NUMBER];

    private String infile;
    private String infile2;
    private String infile3;
    private String infile4;
    private String outfile;
    private String outfile2;

    private String[] pp_file = new String[MAX_PROF];
    private String id;
    private String tempstring;
    private char tempchar;
    private String[] tm_idd = new String[TM_SEQUENCES];

    private int count, weight;

    private double[] ntmg = new double[26];
    private double[] n = new double[26];
    private double stmg, saa;
    private double[][] profile = new double[MAX_PROF][LENGTH + 1];

    /* P values and spans */

    private int pp_antal = 2;
    private int[] span = new int[] { 15, 4 };

    /** Sequences */
    private String[] s = new String[NUMBER];
    private int[] relc = new int[LENGTH];
    private int[] reln = new int[LENGTH];
    private int l, start1, stopp1;
    private int nummer, nr, pos;
    /** Number of positions in alignment */
    private int poss;
    private String[] idd = new String[NUMBER];
    private int[] start = new int[TM_NUMBER];
    private int[] stopp = new int[TM_NUMBER];

    private double[] norm_skillnad = new double[NUMBER];
    private int tm_number;
    private int[][] tm_segment = new int[TM_NUMBER][2];
    private int[][] tms_segment = new int[TM_NUMBER][2];
    private int[] npos = new int[MAXHIT];
    private int[] cpos = new int[MAXHIT];
    private int[] pred_mode = new int[TM_NUMBER];
    private String pred_string;
    private double[][] charges = new double[TM_NUMBER][3];
    private double[] gvhcharge = new double[TM_NUMBER];
    private double[][] mincharges = new double[TM_NUMBER][3];
    private double[][] maxcharges = new double[TM_NUMBER][3];
    private double[] mingvhcharge = new double[TM_NUMBER];
    private double[] maxgvhcharge = new double[TM_NUMBER];

    private int e_spann_min, e_spann_max;

    private double mx_limit, me_limit;
    private int[] ali_ok = new int[LENGTH];

    private int refant;
    private String[] refali = new String[NUMBER];
    private String[] refnamn = new String[NUMBER];

    private static final int OVERLAPP = 2;
    private static final int MAX_TM_LANGD = 30;
    private static final int MIN_TM_LANGD = 25;

    /*
     * Definitioner for PostScript-figurer
     */

    private static final double X_SKALA = 1.7;
    private static final int Y_SKALA = 80;
    private static final int Y_ORIGO = 300;

    private static final double X_STRECKLANGD = 0.04;
    private static final double XL_STRECKLANGD = 0.08;
    private static final double X_STRECKTATHET = 10.0;
    private static final double X_SIFFERTATHET = 50.0;

    private static final int Y_STRECKLANGD = 2;
    private static final double Y_STRECKTATHET = 0.5;

    private static final int V_LABELBREDD = 20;
    private static final int H_LABELBREDD = 5;
    private static final int LABELHOJD = 10;

    private static final int NUMBERSIZE = 8;
    private static final int LABELSIZE = 10;
    private static final int LEFTMARGIN = 50;
    private static final int SIDBREDD = 400;

    private static final int LEG_LINE_LENGTH = 50;

    private static final double TM_MARKERING = 2.0; /*
                                                     * Pos i y-led, dar
                                                     * tm-markeringar sattes
                                                     */
    private static final int TM_ZONE = 4; /* Region omkring TM-segment */

    private StringBuffer fp = new StringBuffer();
    private StringBuffer psfp = new StringBuffer();

    /**
     * length1 -------
     * 
     * Calculates the real medium length of TM segment Eliminates from the
     * calculations sequences with less than 4 positions
     */
    private double length1(int nr, int start, int stopp)

    {
        int i, j, l, ll;
        int[] correct_sequence = new int[MAXHIT];
        int nr_correct;

        /* First, check for sequences with less than 10 a. a. residues */
        for (i = 0; i < MAXHIT; i++) {
            correct_sequence[i] = 0;
        }
        nr_correct = 0;

        for (i = 0; i <= nr; i++) {
            for (j = start, l = 0; j <= stopp; j++)
                if (s[i].charAt(j) != GAP)
                    l++;
            if (l >= 10) {
                correct_sequence[i] = 1;
                nr_correct++;
            }
        }

        /*
         * Second, check for lengths among the sequences that contain >=10 a. a.
         * residues
         */
        for (i = 0, ll = 0; i <= nr; i++)
            if (correct_sequence[i] == 1) {
                for (j = start, l = 0; j <= stopp; j++)
                    if (s[i].charAt(j) != GAP)
                        l++;
                ll += l;
            }

        if (nr_correct != 0)
            return (double) ll / nr_correct;
        else
            return 0;

    }

    private void present_close() {
        if (1 == xml_output) {
            fprintf(fp, "</tmapresult>\n");
        }
    }

    /**
     * present_open ------------ Opens outfile for results
     */
    void present_open() {
        if (1 == xml_output) {
            fprintf(fp, "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
            fprintf(fp, "<tmapresult version=\"1.0\">\n");
            fprintf(fp, "<metainfo>\n");
        }

        fprintf(fp, "RESULTS from program TMAP, edition %s\n\n", UTGAVA);

        fprintf(fp, "Numbers give: a) number of transmembrane segment\n");
        fprintf(fp,
                "              b) start of TM segment (alignment position / residue number)\n");
        fprintf(fp,
                "              c) end of TM segment (alignment position / residue number)\n");
        fprintf(fp,
                "              d) length of TM segment within parentheses\n\n");

        if (1 == xml_output)
            fprintf(fp, "</metainfo>\n");
    }

    /**
     * present3p, utg. 44 ------------------ Presents results from predictions
     */
    private void present3p(int antal, int[] npos, int[] cpos, int poss, int nr,
            String msffile, String[] idd) {
        int i, j;
        if (1 == xml_output)
            fprintf(fp, "<proteinpredtmss>\n");
        else
            fprintf(fp, "PREDICTED TRANSMEMBRANE SEGMENTS\n");

        /*
         * for (i=1; i<=antal; i++) fprintf(fp,"  TM %2d: %4d - %4d  (%4.1f)\n",
         * i,npos[i],cpos[i],length1(nr,npos[i],cpos[i])); fprintf(fp,"\n\n");
         */

        for (j = 0; j <= nr; j++) {
            refpos2(j, poss);
            /*
             * fprintf(fp,"PREDICTED TRANSMEMBRANE SEGMENTS FOR PROTEIN %s\n\n",idd
             * [j]);
             */

            for (i = 1; i <= antal; i++) {
                if (1 == xml_output) {
                    fprintf(fp,
                            " <tmsegment id=\"%d\" startpos=\"%d\" endpos=\"%d\" length=\"%d\" />\n",
                            i, reln[npos[i]], relc[cpos[i]], relc[cpos[i]]
                                    - reln[npos[i]] + 1);
                } else
                    fprintf(fp, "  TM %2d: %4d - %4d (%d)\n", i, reln[npos[i]],
                            relc[cpos[i]], relc[cpos[i]] - reln[npos[i]] + 1);
            }

            if (1 == xml_output)
                fprintf(fp, "</proteinpredtmss>\n");
            else
                fprintf(fp, "\n\n");
        }
    }

    /**
     * Attempt to emulate C out-of-bounds index lookups in multidimensional
     * arrays by adjusting index and selecting the correct sub-array to get
     * value out of.
     * 
     * @param parameters
     * @param which
     * @param index
     * @return
     */
    private double lookup(double[][] parameters, int which, int index) {
        while (index < 0 && which > 0) {
            index += parameters[which].length;
            which -= 1;
        }
        while (index >= parameters[which].length
                && which < parameters.length - 1) {
            index -= parameters[which].length;
            which += 1;
        }
        return parameters[which][index];
    }

    /**
     * peak1 ----- Finds peak value in the vector 'parameter' and returns
     * position of peak value
     */
    private int peak1(int start, int stopp, double[][] parameters, int which) {
        int i, maxpos = 0;
        double maximum;

        maximum = 0;
        for (i = start; i <= stopp; i++) {
            if (lookup(parameters, which, i) > maximum) {
                maxpos = i;
                maximum = lookup(parameters, which, i);
            }
        }
        return maxpos;
    }

    /**
     * vec_to_stst ----------- Transfers information in vector vec[] to start[]
     * and stopp[] Returns number of elements in start[] and stopp[]
     */
    private int vec_to_stst(int[] vec, int[] start, int[] stopp, int length) {
        int flagga, i, index;

        flagga = 0;
        index = 0;

        for (i = 1; i <= length; i++) {
            if ((vec[i] == 1) && (flagga == 0)) {
                flagga = 1;
                start[++index] = i;
            }
            if ((vec[i] == 0) && (flagga == 1)) {
                flagga = 0;
                stopp[index] = i - 1;
            }
        }
        if (flagga == 1)
            stopp[index] = length;

        return index;
    }

    /**
     * weights ------- Calculates number of differences between sequence
     * 'testnr' and all other sequences (Ref. Vingron & Argos, CABIOS 5 (1989)
     * 115-121).
     *
     * s[][] - sequence matrix poss - number of positions in sequences nr - max
     * nr of sequences norm_sk - vector for weights
     *
     */
    void weights(String[] s, int poss, int nr, double[] norm_sk) {
        int i, j, testnr;
        int[] skillnad = new int[NUMBER + 1];
        double summa;

        for (testnr = 0; testnr <= nr; testnr++)
            for (i = 0; i <= nr; i++)
                if (i != testnr)
                    for (j = 1, skillnad[testnr] = 0; j <= poss; j++)
                        if (s[testnr].charAt(j) != s[i].charAt(j))
                            skillnad[testnr]++;

        /*
         * Normalize 'skillnad[]'
         */
        for (i = 0, j = LENGTH * NUMBER; i <= nr; i++)
            if (skillnad[i] < j)
                j = skillnad[i];
        for (i = 0; i <= nr; i++)
            norm_sk[i] = (double) skillnad[i] / j;

        /*
         * Satt Summa av skillnad[] till 1
         */

        for (i = 0, summa = 0; i <= nr; i++)
            summa += norm_sk[i];
        for (i = 0; i <= nr; i++)
            norm_sk[i] = norm_sk[i] / summa;

        for (i = 0; i <= nr; i++)
            printf("Nr %3d   Vikt %5.3f  Skillnader %5d  \n", i, norm_sk[i],
                    skillnad[i]);

    } /* weights */

    private void refpos2(int refnr, int poss)

    {
        int i, temp;

        for (i = 0; i < LENGTH; i++)
            relc[i] = reln[i] = 0;

        temp = 1;
        for (i = 1; i <= poss; i++) {
            reln[i] = temp;
            if (s[refnr].charAt(i) != GAP)
                ++temp;
        }

        temp = 0;
        for (i = 1; i <= poss; i++) {
            if (s[refnr].charAt(i) != GAP)
                ++temp;
            relc[i] = temp;
        }
    }

    /*
     * summa1 ------ Sums the values for 'parameter' in span 'start' to 'stopp'
     */

    private double summa1(int start, int stopp, double[] parameter) {
        double summa = 0;
        int i;

        for (i = start; i <= stopp; i++)
            summa += parameter[i];

        return summa;
    }

    /**
     * plot1 ----- Rutin for PostScript-plot av resultatet
     *
     * pp_file - filnamn pp_antal - antal profiler poss - max pos tm_antal -
     * antal tm-segment tm_segment[][] - matris med start- och slut-positioner
     */
    private String plot1(String ps_file, int pp_antal, String[] pp_file,
            int poss, int tm_antal, int[][] tm_segm, int stand_antal,
            int[][] tms_segm) throws IOException {

        int i, j, page;

        ps_init1();
        for (page = 0; page <= poss / SIDBREDD; page++) {
            ps_init2();

            /* SCALES */

            plot_y_skala((double) -4, 0.00, 2.00, (double) Y_STRECKLANGD,
                    (double) Y_STRECKTATHET);

            if (poss <= (page + 1) * SIDBREDD) {
                plot_y_skala((double) (poss % SIDBREDD) + 4, 0.00, 2.00,
                        (double) -Y_STRECKLANGD, (double) Y_STRECKTATHET);
                plot_x_skala((double) -0.1, (double) 0,
                        (double) (poss % SIDBREDD), (double) X_STRECKLANGD,
                        (double) XL_STRECKLANGD, (double) X_STRECKTATHET,
                        (double) X_SIFFERTATHET, page);
                /* top scale */
                plot_x_skala((double) 2.15, (double) 0,
                        (double) (poss % SIDBREDD), (double) -X_STRECKLANGD,
                        (double) -XL_STRECKLANGD, (double) X_STRECKTATHET,
                        (double) X_SIFFERTATHET, page);
                plot_linetype(3, 3);
                plot_x_linje((double) 0, (double) (poss % SIDBREDD),
                        (double) 1.00);
                plot_linetype(0, 0);
                plot_text((double) (poss % SIDBREDD) / 2, (double) 3.0, ps_file);
            } else {
                plot_y_skala((double) SIDBREDD + 4, 0.00, 2.00,
                        (double) -Y_STRECKLANGD, (double) Y_STRECKTATHET);
                plot_x_skala((double) -0.1, (double) 0, (double) SIDBREDD,
                        (double) X_STRECKLANGD, (double) XL_STRECKLANGD,
                        (double) X_STRECKTATHET, (double) X_SIFFERTATHET, page);
                /* top scale */
                plot_x_skala((double) 2.15, (double) 0, (double) SIDBREDD,
                        (double) -X_STRECKLANGD, (double) -XL_STRECKLANGD,
                        (double) X_STRECKTATHET, (double) X_SIFFERTATHET, page);
                plot_linetype(3, 3);
                plot_x_linje((double) 0, (double) SIDBREDD, (double) 1.00);
                plot_linetype(0, 0);
                plot_text((double) (SIDBREDD) / 2, (double) 3.0, ps_file);
            }

            /* Write TM markings */

            for (i = 1; i <= tm_antal; i++) {

                if ((tm_segm[i][0] >= page * SIDBREDD)
                        && (tm_segm[i][0] <= (page + 1) * SIDBREDD))
                    if (tm_segm[i][1] <= (page + 1) * SIDBREDD)
                        plot_tmbar(tm_segm[i][0] % SIDBREDD, tm_segm[i][1]
                                % SIDBREDD, (double) TM_MARKERING, 0);
                    else
                        plot_tmbar(tm_segm[i][0] % SIDBREDD, SIDBREDD,
                                (double) TM_MARKERING, 1);

                if ((tm_segm[i][1] >= page * SIDBREDD)
                        && (tm_segm[i][1] <= (page + 1) * SIDBREDD))
                    if (tm_segm[i][0] >= page * SIDBREDD)
                        plot_tmbar(tm_segm[i][0] % SIDBREDD, tm_segm[i][1]
                                % SIDBREDD, (double) TM_MARKERING, 0);
                    else
                        plot_tmbar(0, tm_segm[i][1] % SIDBREDD,
                                (double) TM_MARKERING, 2);
            }

            /* Skriv TM-"sann"-markering */
            /*
             * for (i=1; i<=stand_antal; i++) {
             * 
             * if ((tms_segm[i][0]>=page*SIDBREDD) &&
             * (tms_segm[i][0]<=(page+1)*SIDBREDD)) if
             * (tms_segm[i][1]<=(page+1)*SIDBREDD)
             * plot_tmbar(tms_segm[i][0]%SIDBREDD
             * ,tms_segm[i][1]%SIDBREDD,(double)TM_MARKERING-0.1,0); else
             * plot_tmbar
             * (tms_segm[i][0]%SIDBREDD,SIDBREDD,(double)TM_MARKERING-0.1,1);
             * 
             * if ((tms_segm[i][1]>=page*SIDBREDD) &&
             * (tms_segm[i][1]<=(page+1)*SIDBREDD)) if
             * (tms_segm[i][0]>=page*SIDBREDD)
             * plot_tmbar(tms_segm[i][0]%SIDBREDD
             * ,tms_segm[i][1]%SIDBREDD,(double)TM_MARKERING-0.1,0); else
             * plot_tmbar(0,tms_segm[i][1]%SIDBREDD,(double)TM_MARKERING-0.1,2);
             * }
             */

            /* PROFILES */

            for (j = 0; j < pp_antal; j++) {
                plot_linetype(linjetyp[j][0], linjetyp[j][1]);
                if (poss > (page + 1) * SIDBREDD)
                    plot_profile(j, page * SIDBREDD + 1, (page + 1) * SIDBREDD,
                            page, span[j]);
                else
                    plot_profile(j, page * SIDBREDD + 1, poss, page, span[j]);
            }

            plot_legend((double) 150, (double) -0.5, pp_antal, pp_file);

            ps_sidbrytning();

        } /* page */

        return psfp.toString();

    } /* plot1 */

    private int pred1(double m_limit, double ml_limit, double e_limit, int nr)

    {
        int i, j, k, hitnr, length, tm_ant;
        boolean flag;
        int[] start = new int[MAXHIT];
        int[] stopp = new int[MAXHIT];
        int[] hitposs = new int[LENGTH];
        int avstand, mitt, start0;

        int[] start_e_pos = new int[MAXHIT];
        int[] stopp_e_pos = new int[MAXHIT];

        double sum;

        int count, count2, tempN, tempC;

        int starttmp, stopptmp, temp;

        hitnr = 0;
        for (i = 0; i < TM_NUMBER; i++)
            pred_mode[i] = 0;

        /* Find peak values */
        for (i = 1; i <= poss; i++) {
            if (profile[0][i] > m_limit) {
                hitposs[i] = 1;
            } else {
                hitposs[i] = 0;
            }
        }

        /* Smoothing: Disregard 1 or 2 consequtive positions in vector hitposs[] */
        for (i = 3; i <= poss - 1; i++)
            if ((hitposs[i - 2] == 1) && (hitposs[i + 1] == 1))
                hitposs[i] = hitposs[i - 1] = 1;
        for (i = 2; i <= poss - 1; i++)
            if ((hitposs[i - 1] == 1) && (hitposs[i + 1] == 1))
                hitposs[i] = 1;

        /* Transform hitposs[] to TM vector */
        for (i = 0; i < MAXHIT; i++)
            start[i] = stopp[i] = npos[i] = cpos[i] = 0;
        tm_ant = vec_to_stst(hitposs, start, stopp, poss);

        for (i = 1; i <= tm_ant; i++)
            try {
                pred_mode[i] = pred_mode[i] | 1;
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }

        /* Remove start[] & stopp[] with length <=M_KORT2_LIMIT */
        for (i = 1; i <= tm_ant; i++)
            if (stopp[i] - start[i] < M_KORT2_LIMIT - 1) {

                /*
                 * Correct for if strictly conserved charges are present, thus
                 * reducing the mean value
                 */

                count2 = 0;

                count = stopp[i] - start[i] + 1;
                for (j = stopp[i] + 1; j <= poss && profile[0][j] > mx_limit; j++)
                    if (profile[0][j] > mx_limit)
                        count++;
                tempC = j - 1;
                for (j = start[i] - 1; j > 0 && profile[0][j] > mx_limit; j--)
                    if (profile[0][j] > mx_limit)
                        count++;
                tempN = j + 1;

                mitt = (tempC - tempN) / 2 + tempN;

                count2 = 0;
                if (count > 8)
                    if (mitt > 8)
                        for (j = mitt - 8; j <= mitt + 8 && j <= poss; j++)
                            if (all_charged(j, nr))
                                count2++;

                /* ... this was not the case ... */

                if (count2 == 0) {
                    for (j = i; j <= tm_ant - 1; j++) {
                        try {
                            start[j] = start[j + 1];
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                        try {
                            stopp[j] = stopp[j + 1];
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                        try {
                            pred_mode[j] = pred_mode[j + 1];
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }
                    i--;
                    tm_ant--;
                }

            }

        /*
         * 3. Starting in 'start[]' and 'stopp[]', expand N- and C-terminally -
         * each step is taken in the direction of highest profile[0] value - as
         * long 'over_limit' is true until langd=M_SPAN
         */

        stopp[0] = 0;
        start[tm_ant + 1] = pos;

        for (i = 1; i <= tm_ant; i++) {
            flag = true;
            while (flag) {
                if ((profile[0][start[i] - 1] > ml_limit)
                        || (profile[0][stopp[i] + 1] > ml_limit))
                    if (profile[0][start[i] - 1] > profile[0][stopp[i] + 1])
                        if ((start[i] > 1)
                                && (profile[0][start[i] - 1] > ml_limit)
                                && (start[i] > stopp[i - 1] + FORLC))
                            start[i]--;
                        else if ((stopp[i] < poss)
                                && (profile[0][stopp[i] + 1] > ml_limit)
                                && (stopp[i] < start[i + 1] - FORLN))
                            stopp[i]++;
                        else
                            flag = false;
                    else if ((stopp[i] < poss)
                            && (profile[0][stopp[i] + 1] > ml_limit)
                            && (stopp[i] < start[i + 1] - FORLN))
                        stopp[i]++;
                    else if ((start[i] > 1)
                            && (profile[0][start[i] - 1] > ml_limit)
                            && (start[i] > stopp[i - 1] + FORLC))
                        start[i]--;
                    else
                        flag = false;
                else
                    flag = false;
            }
        }

        /*
         * 4. Elongate with N_FOSFAT and C_FOSFAT, respectively, to correct for
         * that the prediction hitherto has been focused to find only the
         * hydrophobic portion of the transmembrane segment
         */

        for (i = 1; i <= tm_ant; i++) {
            if (start[i] > N_FOSFAT)
                start[i] -= N_FOSFAT;
            if (stopp[i] < poss - C_FOSFAT)
                stopp[i] += C_FOSFAT;
        }

        /*
         * 5.1. Search for Pe values
         */

        for (i = 1; i <= tm_ant; i++) {
            mitt = start[i] + (stopp[i] - start[i]) / 2;
            start_e_pos[i] = peak1(mitt - 20, mitt, profile, 1);
            stopp_e_pos[i] = peak1(mitt, mitt + 20, profile, 1);

            /* Use the Pe values, if they are good */

            if ((profile[1][start_e_pos[i]] >= e_limit)
                    && (profile[1][stopp_e_pos[i]] >= e_limit)) {
                if ((length1(nr, start_e_pos[i], stopp_e_pos[i]) >= E_SPANN_MIN)
                        && (length1(nr, start_e_pos[i], stopp_e_pos[i]) <= E_SPANN_MAX)) {
                    start[i] = start_e_pos[i] - START_E_KORR;
                    stopp[i] = stopp_e_pos[i] + STOPP_E_KORR;
                }

            } else {

                if (profile[1][start_e_pos[i]] >= e_limit)
                    if (Math.abs(start_e_pos[i] - start[i]) < E_STST_DIFF)
                        start[i] = start_e_pos[i] - START_E_KORR;

                if (profile[1][stopp_e_pos[i]] >= e_limit)
                    if (Math.abs(stopp_e_pos[i] - stopp[i]) < E_STST_DIFF)
                        stopp[i] = stopp_e_pos[i] + STOPP_E_KORR;
            }

        } /* i, E-varden */

        /* Check for Pe values */

        for (i = 20; i <= poss - 20; i++) {

            starttmp = peak1(i - 19, i, profile, 1);
            stopptmp = peak1(i + 1, i + 20, profile, 1);
            temp = 0;

            if ((profile[1][starttmp] > 1.15) && (profile[1][stopptmp] > 1.15))
                if ((length1(nr, starttmp, stopptmp) >= E_SPANN_MIN /* >17 */)
                        && (length1(nr, starttmp, stopptmp) <= e_spann_max /*
                                                                            * <30
                                                                            */))
                    for (j = starttmp + 2, temp = 1; j <= stopptmp - 2; j++)
                        if (profile[0][j] < 1.08) /*
                                                   * <1.08 gor att helix 7 i
                                                   * gt74 hittas, >=1.09 gar ej
                                                   */
                            temp = 0;

            if (temp == 1)
                if (!(tm_in_vector(start, stopp, tm_ant, starttmp, stopptmp)))
                    tm_ant = insert_in_vector(start, stopp, tm_ant, starttmp,
                            stopptmp, pred_mode, 16);

        }

        /*
         * 6.2. Correction for overlap
         */

        for (i = 2; i <= tm_ant; i++)
            if (start[i] < stopp[i - 1]) {
                stopp[i - 1] = stopp[i];
                for (j = i; j < tm_ant; j++) {
                    start[j] = start[j + 1];
                    stopp[j] = stopp[j + 1];
                    pred_mode[j] = pred_mode[j + 1];
                }
                tm_ant--;
                i--;
            }

        /*
         * 6.3. Divide segments long enough to contain several tm segments
         */

        for (i = 1; i <= tm_ant; i++) {
            length = (int) length1(nr, start[i], stopp[i]);
            for (j = 10; j >= 2; j--) {
                if (length >= j * M_SPANN + (j - 1) * (N_SPANN + C_SPANN - 1)) {

                    /*
                     * For att korrigera for turn precis i membrankanten kan
                     * langdkriteriet justeras litet grand:
                     */
                    /* N_SPANN+C_SPANN missar en helix i k-kanalerna */
                    /* N_SPANN+C_SPANN-2 ger en extra helix i am */
                    /* N_SPANN+C_SPANN-1 fungerar i bada fallen ... */

                    /*
                     * dela upp tm_segmentet symmetriskt, ty enklast kan
                     * finjusteras senare ....
                     */

                    /* gor plats for de nya segmenten */
                    tm_ant += j - 1;
                    for (k = tm_ant; k >= i + 1; k--) {
                        // ignoring errors from out of bounds indexes, which are
                        // tolerated in C land.
                        try {
                            start[k] = start[k - (j - 1)];
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                        try {
                            stopp[k] = stopp[k - (j - 1)];
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                        try {
                            pred_mode[k] = pred_mode[k - (j - 1)];
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }

                    avstand = length / j;
                    start0 = start[i];

                    for (k = 1; k <= j; k++) {
                        mitt = avstand / 2 + avstand * (k - 1);
                        start[i + k - 1] = start0 + mitt - N_FORLANGNING;
                        stopp[i + k - 1] = start0 + mitt + C_FORLANGNING;
                        pred_mode[i + k - 1] = pred_mode[i + k - 1] | 8;
                    }

                    j = 0;
                } /* if */
            } /* j */
        } /* i */

        /*
         * 7. Remove too short segments
         */

        for (i = 1; i <= tm_ant; i++)
            if ((length1(nr, start[i], stopp[i]) < M_KORT3_LIMIT)) {
                for (j = i; j <= tm_ant - 1; j++) {
                    start[j] = start[j + 1];
                    stopp[j] = stopp[j + 1];
                    pred_mode[j] = pred_mode[j + 1];
                }
                i--;
                tm_ant--;
            }

        /*
         * 8. Shorten segments, longer than M_SPANN aa
         */

        for (i = 1; i <= tm_ant; i++)
            if ((length1(nr, start[i], stopp[i]) > M_LANG1_LIMIT)) {
                /* Forst kolla om segmentet tangerar forutvarande */
                if (i > 1)
                    if (start[i] < stopp[i - 1])
                        start[i] = stopp[i - 1];

                sum = 0;
                start0 = start[i];
                for (j = start[i]; j <= stopp[i] - M_LANG1_LIMIT + 1; j++)
                    if (summa1(j, j + M_LANG1_LIMIT - 1, profile[0]) > sum) {
                        sum = summa1(j, j + M_LANG1_LIMIT - 1, profile[0]);
                        start0 = j;
                    }
                start[i] = start0;
                stopp[i] = start0 + M_LANG1_LIMIT - 1;
                pred_mode[i] = pred_mode[i] | 128;
            }

        for (i = 1; i <= tm_ant; i++) {
            npos[i] = start[i];
            cpos[i] = stopp[i];
        }

        return tm_ant;
    }

    /**
     * insert_in_vector ---------------- Insert segment in TM vector
     */
    private int insert_in_vector(int[] start, int[] stopp, int max,
            int starttmp, int stopptmp, int[] pred, int predparameter) {
        int i, j;

        for (i = 1; i <= max - 1; i++)
            if (starttmp > start[i])
                if (starttmp < start[i + 1]) {
                    for (j = max; j >= i + 1; j--) {
                        start[j + 1] = start[j];
                        stopp[j + 1] = stopp[j];
                        pred[j + 1] = pred[j];
                    }
                    start[i + 1] = starttmp;
                    stopp[i + 1] = stopptmp;
                    pred[i + 1] = predparameter;
                    return max + 1;
                }

        /* starttmp<=start[i] */

        for (j = max; j >= 1; j--) {
            start[j + 1] = start[j];
            stopp[j + 1] = stopp[j];
            pred[j + 1] = pred[j];
        }
        start[1] = starttmp;
        stopp[1] = stopptmp;
        pred[1] = predparameter;
        return max + 1;

    }

    /**
     * tm_in_vector ------------ Checks if segment already in TM vector
     */

    private boolean tm_in_vector(int[] start, int[] stopp, int max,
            int starttmp, int stopptmp) {
        int i;
        boolean temp;

        temp = false;
        for (i = 1; i <= max; i++)
            if ((Math.abs(start[i] - starttmp) < 7)
                    && (Math.abs(stopp[i] - stopptmp) < 7))
                temp = true;

        return temp;
    }

    /**
     * all_charged ----------- Calculates if all residues at an alignment
     * position are identical and one of DEKRQN
     * 
     * Returns 1 if so, otherwise 0
     */
    private boolean all_charged(int pos, int nr) {
        int i, likhet;
        for (i = 1, likhet = 1; i <= nr; i++)
            if (s[0].charAt(pos) != s[i].charAt(pos))
                likhet = 0;
        if (likhet == 1)
            if ((s[0].charAt(pos) == 'D') || (s[0].charAt(pos) == 'E')
                    || (s[0].charAt(pos) == 'K') || (s[0].charAt(pos) == 'R'))
                return true;
            else
                return false;
        else
            return false;
    }

    /**
     * align_rel ---------
     */
    void align_rel(int antal, int poss, int span)

    {
        int nr, ok, pos;

        for (pos = 1; pos <= poss - span + 1; pos++) {
            for (nr = 0, ok = 0; nr <= antal; nr++)
                if (s[nr].charAt(pos) != GAP)
                    ok++;
            if ((double) ok / (double) nr > ALI_MINIMUM)
                ali_ok[pos] = 1;
            else
                ali_ok[pos] = 0;
        }
    }

    private void topo_calculate(String[] s, int seqln, int nr, int nr_tms,
            int[] npos, int[] cpos) {

        int i, n, tm;
        double sum;
        int[][] tms = new int[MAXHIT][2];
        double[] aa1 = new double[26];
        double[] aa2 = new double[26];
        double[] aa1p = new double[26];
        double[] aa2p = new double[26];
        double[] ratio = new double[26];

        int pred_ic, pred_ec, final_pred, k, r;
        int aatyp[] = new int[10];
        String[] kstring = new String[] { "K in\0", "    \0", "K out\0" };
        String[] rstring = new String[] { "R in\0", "    \0", "R out\0" };

        double[] threshold = new double[26];
        double[] inv_threshold = new double[26];

        /* Convert npos and cpos to tms[][] */

        for (tm = 1; tm <= nr_tms; tm++) {
            tms[tm][0] = npos[tm];
            tms[tm][1] = cpos[tm];
        }

        for (i = 0; i < 26; i++)
            aa1[i] = aa2[i] = aa1p[i] = aa2p[i] = ratio[i] = 0;
        final_pred = 0;
        k = r = 0;

        tms[0][1] = 0;
        tms[nr_tms + 1][0] = seqln;
        if (tms[nr_tms + 1][0] - tms[nr_tms][1] > CONSIDER)
            tms[nr_tms + 1][0] = tms[nr_tms][1] + CONSIDER;

        /*
         * I. regions before odd-numbered tms, i.e. before 1, between 2 and 3, 4
         * and 5 etc.
         */

        for (tm = 1; tm <= nr_tms + 1; tm += 2)
            for (i = tms[tm - 1][1] + 1; i < tms[tm][0]; i++)
                for (n = 0; n < nr; n++)
                    if (s[n].charAt(i) != GAP)
                        aa1[s[n].charAt(i) - 65] += norm_skillnad[n];

        /*
         * II. regions before even-numbered tms, i.e. between 1 and 2, 3 and 4
         * etc.
         */

        for (tm = 2; tm <= nr_tms + 1; tm += 2)
            for (i = tms[tm - 1][1] + 1; i < tms[tm][0]; i++)
                for (n = 0; n < nr; n++)
                    if (s[n].charAt(i) != GAP)
                        aa2[s[n].charAt(i) - 65] += norm_skillnad[n];

        for (i = 0, sum = 0; i < 26; i++)
            sum += aa1[i];
        if (sum != 0)
            for (i = 0; i < 26; i++)
                aa1p[i] = aa1[i] * 100 / sum;

        for (i = 0, sum = 0; i < 26; i++)
            sum += aa2[i];
        if (sum != 0)
            for (i = 0; i < 26; i++)
                aa2p[i] = aa2[i] * 100 / sum;

        /*
         * for (i=0; i<26; i++) printf("%c %4.1f %4.1f\n",i+65,aa1p[i],aa2p[i]);
         */

        /* Threshold values */

        /* Ala, Cys */
        threshold[0] = 1.55;
        inv_threshold[0] = 1 / threshold[0];
        threshold[2] = 2.85;
        inv_threshold[2] = 1 / threshold[2];

        /* Asp, Phe, Gly, Asn, Pro, Val, Trp, Tyr */
        threshold[3] = 1.45;
        inv_threshold[3] = 1 / threshold[3];
        threshold[5] = 1.15;
        inv_threshold[5] = 1 / threshold[5];
        threshold[6] = 1.45;
        inv_threshold[6] = 1 / threshold[6];
        threshold[13] = 1.25;
        inv_threshold[13] = 1 / threshold[13];
        threshold[15] = 1.50;
        inv_threshold[15] = 1 / threshold[15];
        threshold[21] = 1.45;
        inv_threshold[21] = 1 / threshold[21];
        threshold[22] = 2.20;
        inv_threshold[22] = 1 / threshold[22];
        threshold[24] = 1.45;
        inv_threshold[24] = 1 / threshold[24];

        /* Calculate ratios */

        for (i = 0; i < 26; i++)
            if (aa2p[i] != 0)
                ratio[i] = aa1p[i] / aa2p[i];
            else
                ratio[i] = 10.0;

        pred_ic = pred_ec = 0;

        /* Ala, Cys */
        aatyp[0] = 0;
        aatyp[1] = 2;
        for (i = 0; i <= 1; i++) {
            if (ratio[aatyp[i]] >= threshold[aatyp[i]])
                pred_ic++;
            if ((ratio[aatyp[i]] <= inv_threshold[aatyp[i]])
                    && (ratio[aatyp[i]] != 0))
                pred_ec++;
        }

        /* Asp, Phe, Gly, Asn, Pro, Val, Trp, Tyr */
        aatyp[0] = 3;
        aatyp[1] = 5;
        aatyp[2] = 6;
        aatyp[3] = 13;
        aatyp[4] = 15;
        aatyp[5] = 21;
        aatyp[6] = 22;
        aatyp[7] = 24;
        for (i = 0; i <= 7; i++) {
            if (ratio[aatyp[i]] >= threshold[aatyp[i]])
                pred_ec++;
            if ((ratio[aatyp[i]] <= inv_threshold[aatyp[i]])
                    && (ratio[aatyp[i]] != 0))
                pred_ic++;
        }

        /* Lys, Arg */
        if (ratio[10] >= 1.00) {
            pred_ic++;
            k = -1;
        }
        if ((ratio[10] < 1.00) && (ratio[10] != 0)) {
            pred_ec++;
            k = 1;
        }
        if (ratio[17] >= 1.00) {
            pred_ic++;
            r = -1;
        }
        if ((ratio[17] < 1.00) && (ratio[17] != 0)) {
            pred_ec++;
            r = 1;
        }

        if (pred_ic == pred_ec) {
            if ((k == 1) && (r == 1))
                final_pred = 1; /* ec */
            else if ((k == -1) && (r == -1))
                final_pred = -1; /* ic */
        } else {
            if (pred_ic > pred_ec)
                final_pred = -1; /* ic */
            if (pred_ec > pred_ic)
                final_pred = 1; /* ec */
        }

        if (final_pred == -1) {
            if (1 == xml_output) {
                fprintf(fp,
                        "<topology prediction=\"Nin\" contribs_in=\"%d\" contribs_out=\"%d\" k=\"%s\" r=\"%s\" />\n",
                        pred_ic, pred_ec, kstring[k + 1], rstring[r + 1]);
            } else {
                fprintf(fp,
                        "PREDICTED TOPOLOGY:  Nin   (contributions: %2d in %2d out  %s  %s)\n",
                        pred_ic, pred_ec, kstring[k + 1], rstring[r + 1]);
                fprintf(fp,
                        "'in' corresponds to 'cytosolic'; 'out' corresponds to 'non-cytosolic'\n\n");
            }
        }
        if (final_pred == 1) {
            if (1 == xml_output) {
                fprintf(fp,
                        "<topology prediction=\"Nout\" contribs_in=\"%d\" contribs_out=\"%d\" k=\"%s\" r=\"%s\" />\n",
                        pred_ic, pred_ec, kstring[k + 1], rstring[r + 1]);
            } else {
                fprintf(fp,
                        "PREDICTED TOPOLOGY:  Nout  (contributions: %2d in %2d out  %s  %s)\n",
                        pred_ic, pred_ec, kstring[k + 1], rstring[r + 1]);
                fprintf(fp,
                        "'in' corresponds to 'cytosolic'; 'out' corresponds to 'non-cytosolic'\n\n");
            }
        }
        if (final_pred == 0) {
            if (1 == xml_output) {
                fprintf(fp,
                        "<topology prediction=\"NO CERTAIN TOPOLOGY PREDICTION\" contribs_in=\"%d\" contribs_out=\"%d\" k=\"%s\" r=\"%s\" />\n",
                        pred_ic, pred_ec, kstring[k + 1], rstring[r + 1]);
            } else {
                fprintf(fp,
                        "NO CERTAIN TOPOLOGY PREDICTION (contributions: %2d in %2d out  %4s %4s)\n\n",
                        pred_ic, pred_ec, kstring[k + 1], rstring[r + 1]);
            }
        }
    }

    /**
     * profile2 -------- Calculates mean values of 'P[]' over 'span' a.a. and
     * stores the result in 'profile[]'.
     * 
     * Calculates on all sequences of the alignment Ignores gaps
     * 
     * prof - number of profile antal - number of sequences in alignment poss -
     * number of positions in sequence span - length of span for profile to be
     * calculated upon s[][] - sequence data P[] - values for profile profile[]
     * - result vector, result stored in pos closest to centre of segment
     */
    void profile2(int prof, int antal, int poss, int span) {
        int bin, count, i, j, nr;
        bin = 0;
        int[] flagga = new int[LENGTH + 1];
        double prof_temp;
        double[] summa_vikt = new double[LENGTH + 1];

        for (i = 1; i <= poss; i++) {
            profile[prof][i] = 0;
            flagga[i] = 0;
            summa_vikt[i] = 0;
        }

        for (nr = 0; nr <= antal; nr++)
            for (i = 1; i <= poss - span + 1; i++) {
                if ((s[nr].charAt(i) >= 'A') && (s[nr].charAt(i) <= 'Z')) {
                    for (j = 0, count = 0, prof_temp = 0; count < span
                            && i + j <= poss; j++)
                        if ((s[nr].charAt(i + j) >= 'A')
                                && (s[nr].charAt(i + j) <= 'Z')) {
                            prof_temp += P[prof][s[nr].charAt(i + j) - 'A']
                                    * norm_skillnad[nr];
                            if (count == span / 2)
                                bin = i + j;
                            count++;
                        }
                    if (count == span) {
                        flagga[bin] = 1;
                        profile[prof][bin] += prof_temp / count;
                        summa_vikt[bin] += norm_skillnad[nr];
                    }
                }
            }

        for (i = 1; i <= poss; i++)
            if ((flagga[i] == 0) || (ali_ok[i] == 0))
                profile[prof][i] = NOLLVARDE;
            else
                profile[prof][i] = profile[prof][i] / summa_vikt[i];

    }

    private void plot_profile(int prof, int startpos, int slutpos, int page,
            int span) {

    }

    private static void printf(String s, Object... args) {
        printf(String.format(s, args));
    }

    private static void printf(String s) {
        System.out.print(s);
    }

    private static String gets() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        return reader.readLine();
    }

    public static void main(String[] args) throws IOException {
        new TMAP().run(args);
    }

    public void run(String[] args) throws IOException {
        if (args.length < 2) {
            printf("Please enter name of the alignment file (.fasta) -> ");
            infile = gets();
            printf("\nPlease enter name of result file (.res) -> ");
            outfile = gets();
        } else {
            infile = args[0];
            outfile = args[1];
        }
        boolean xml = false;
        /* Check for -xml output option flag */
        if (args.length == 3) {
            if (args[2].equalsIgnoreCase("-xml")) {
                xml = true;
            }
        }
        if (!infile.endsWith(".fasta")) {
            infile = infile + ".fasta";
        }
        if (xml_output == 1) {
            if (!outfile.endsWith(".xml")) {
                outfile = outfile + ".xml";
            }
        } else {
            if (!outfile.endsWith(".res")) {
                outfile = outfile + ".res";
            }
        }

        FileInputStream fin = new FileInputStream(infile);
        String sequenceData = IOUtils.toString(fin).replaceAll("[\r\n]", "");
        fin.close();
        String result = run(infile, sequenceData, xml);
        File output = new File(outfile);
        if (!output.exists()) {
            output.createNewFile();
        }
        FileOutputStream fout = new FileOutputStream(output);
        fout.write(result.getBytes("utf-8"));
        fout.close();
    }

    public String run(String filename, String sequenceData, boolean xml)
            throws IOException {
        int j;
        int[] stand_tmant = new int[TM_NUMBER];
        int n_spann, m_spann, c_spann;
        double m_limit, ml_limit, e_limit;
        int stand_nr;
        // char *pdf_filename;
        poss = sequenceData.length() - 1;
        printf("Program TMAP, version %s, to predict transmembrane segments from .fasta file.\n\n",
                UTGAVA);
        printf("The program reads a single sequence of the FASTA sequence format\n");
        printf("and predicts membrane-spanning regions according to the algorithm in\n");
        printf("Persson & Argos (1994), J. Mol. Biol. 237, 182-192 and\n");
        printf("Persson & Argos (1996), Prot. Sci. 5, 363-371.\n\n");
        printf("A result file and a PostScript plot file are created.\n\n");
        if (poss >= 10000) {
            poss = 9999;
        }
        if (xml) {
            this.xml_output = 1;
        }

        n_spann = N_SPANN;
        m_spann = M_SPANN;
        c_spann = C_SPANN;

        e_spann_min = E_SPANN_MIN;
        e_spann_max = E_SPANN_MAX;

        m_limit = 1.23;
        ml_limit = 1.17;
        e_limit = 1.07;

        mx_limit = 1.18;
        me_limit = 1.10;
        nr = 0;
        s[0] = sequenceData.toUpperCase();
        test_namn = infile;
        // stand_nr = -1;
        stand_nr = 0;

        if (nr > 0)
            weights(s, poss, nr, norm_skillnad);
        else
            norm_skillnad[0] = 1;

        printf("Calculating on alignment ... \n");

        for (j = 0; j < pp_antal; j++) {
            align_rel(nr, poss, span[j]);
            profile2(j, nr, poss, span[j]);
        }

        tm_number = pred1(m_limit, ml_limit, e_limit, nr);
        /* printf ("nr  %d    tms  %d    nr_tms  %d\n",nr,tms,tm_number); */

        present_open();

        if (tm_number > 0) {
            topo_calculate(s, poss, nr + 1, tm_number, npos, cpos);
            present3p(tm_number, npos, cpos, poss, nr, infile, idd);

        } else {
            if (1 == xml_output) {
                fprintf(fp,
                        "<error msg=\"NO TRANSMEMBRANE SEGMENTS PREDICTED.\" />\n");
            } else
                fprintf(fp, "NO TRANSMEMBRANE SEGMENTS PREDICTED.\n\n");
        }

        for (j = 1; j <= tm_number; j++) {
            tm_segment[j][0] = npos[j] + N_SPANN;
            tm_segment[j][1] = cpos[j] - C_SPANN;
        }

        /* PLOTRUTIN */
        // TODO: refactor postscript generation into separate method
        printf("PS file: %s\n", filename + ".ps");
        plot1(filename + ".ps", pp_antal, pp_file, poss, tm_number, tm_segment,
                stand_tmant[stand_nr], tms_segment);

        present_close();
        return fp.toString();

    } /* END OF MAIN */

    /*
     * ps_init ------- Initierar PostScript-fil
     */

    void ps_init1() {
        fprintf(psfp, "%%! \n");
    }

    void ps_init2() {
        fprintf(psfp, "newpath\n90 rotate\n30 -600 translate\n");
        fprintf(psfp,
                "/Helvetica findfont\n%d scalefont setfont\n0.5 setlinewidth\n",
                NUMBERSIZE);
        fprintf(psfp, "0 0 moveto\n");
    }

    void ps_end() {
        fprintf(psfp, "stroke\nshowpage\n");
    }

    void ps_sidbrytning() {
        fprintf(psfp, "stroke\nshowpage\n");
    }

    /* PLT */

    /*
     * plot_legend -----------
     */

    void plot_legend(double x, double y, int antal, String[] namn) {
        int i;

        for (i = 0; i < antal; i++) {
            plot_linetype(linjetyp[i][0], linjetyp[i][1]);
            fprintf(psfp, "%f %f moveto\n", x * X_SKALA + LEFTMARGIN, y
                    * Y_SKALA + Y_ORIGO - NUMBERSIZE * i);
            fprintf(psfp, "%f %f lineto\n", x * X_SKALA + LEFTMARGIN
                    + LEG_LINE_LENGTH, y * Y_SKALA + Y_ORIGO - NUMBERSIZE * i);
            fprintf(psfp, "%d 2 div neg 0 exch rmoveto ( %s %d) show\n",
                    NUMBERSIZE, namn[i], span[i]);
            fprintf(psfp, "stroke\n");
        }

    }

    /*
     * plot_text ---------
     */

    void plot_text(double x, double y, String str) {
        fprintf(psfp, "%f %f moveto\n", x * X_SKALA + LEFTMARGIN, y * Y_SKALA
                + Y_ORIGO);
        fprintf(psfp, "(%s) dup stringwidth pop 2 div neg 0 rmoveto show\n",
                str);
    }

    /*
     * plot_linetype -------------
     */

    void plot_linetype(int a, int b) {
        if ((a > 0) && (b > 0))
            fprintf(psfp, "[%d %d] 0 setdash\n", a, b);
        else
            fprintf(psfp, "[] 0 setdash\n");
    }

    /*
     * plot_tmbar ----------
     * 
     * parameter 0, normalt 1, bryt i hogerkant 2, bryt i vansterkant
     */

    void plot_tmbar(int x1, int x2, double y, int parameter) {
        fprintf(psfp, "stroke\n1.5 setlinewidth\n");
        plot_x_linje((double) x1, (double) x2, (double) y);
        fprintf(psfp, "0.5 setlinewidth\n");
        if (parameter == 0)
            plot_x_linje((double) x1 - TM_ZONE, (double) x2 + TM_ZONE,
                    (double) y);
        else if (parameter == 1)
            plot_x_linje((double) x1 - TM_ZONE, (double) x2, (double) y);
        else if (parameter == 2)
            plot_x_linje((double) x1, (double) x2 + TM_ZONE, (double) y);

    }

    /*
     * plot_x_linje ------------
     */

    void plot_x_linje(double xmin, double xmax, double y) {
        /* fprintf(psfp,"gsave\n[3] 0 setdash\n"); */
        fprintf(psfp, "%f %f moveto\n%f %f lineto\n", xmin * X_SKALA
                + LEFTMARGIN, y * Y_SKALA + Y_ORIGO, xmax * X_SKALA
                + LEFTMARGIN, y * Y_SKALA + Y_ORIGO);
        fprintf(psfp, "stroke\n");
    }

    /*
     * plot_x_skala ------------
     * 
     * y - position i y-led (uppskalat) xmax - max x-varde (uppskalat) xmin -
     * min x-varde (uppskalat) strecklangd - langd av streck i aktuell mattenhet
     * strecktathet - avstand mellan strecken (samma skala som y-varden)
     */

    void plot_x_skala(double y, double xmin, double xmax, double strecklangd,
            double l_strecklangd, double strecktathet, double siffertathet,
            int page) {
        double i;
        fprintf(psfp, "%f %f moveto\n%f %f lineto\n", xmin * X_SKALA
                + LEFTMARGIN, y * Y_SKALA + Y_ORIGO, xmax * X_SKALA
                + LEFTMARGIN, y * Y_SKALA + Y_ORIGO);
        for (i = xmin; i <= xmax; i += strecktathet) {
            fprintf(psfp, "%f %f moveto\n%f %f lineto\n", i * X_SKALA
                    + LEFTMARGIN, y * Y_SKALA + Y_ORIGO, i * X_SKALA
                    + LEFTMARGIN, (y + strecklangd) * Y_SKALA + Y_ORIGO);
            if (((int) i % (int) siffertathet) == 0) {
                if (strecklangd >= 0)
                    fprintf(psfp,
                            "%f %f moveto\n(%d) dup stringwidth pop 2 div neg 0 rmoveto show\n",
                            i * X_SKALA + LEFTMARGIN, y * Y_SKALA + Y_ORIGO
                                    - NUMBERSIZE, (int) i + page * SIDBREDD);
                else
                    fprintf(psfp,
                            "%f %f moveto\n(%d) dup stringwidth pop 2 div neg 0 rmoveto show\n",
                            i * X_SKALA + LEFTMARGIN, y * Y_SKALA + Y_ORIGO
                                    + NUMBERSIZE, (int) i + page * SIDBREDD);

                fprintf(psfp, "%f %f moveto\n%f %f lineto\n", i * X_SKALA
                        + LEFTMARGIN, y * Y_SKALA + Y_ORIGO, i * X_SKALA
                        + LEFTMARGIN, (y + l_strecklangd) * Y_SKALA + Y_ORIGO);
            }
        }
        fprintf(psfp, "stroke\n");
    }

    /*
     * plot_y_skala ------------
     * 
     * x - position i x-led (uppskalat) ymax - max y-varde (uppskalat) ymin -
     * min y-varde (uppskalat) strecklangd - langd av streck i aktuell mattenhet
     * strecktathet - avstand mellan strecken (samma skala som y-varden)
     */

    void plot_y_skala(double x, double ymin, double ymax, double strecklangd,
            double strecktathet) {
        double i, label_col;
        if (strecklangd > 0)
            label_col = x * X_SKALA - V_LABELBREDD + LEFTMARGIN;
        else
            label_col = x * X_SKALA + H_LABELBREDD + LEFTMARGIN;
        fprintf(psfp, "%f %f moveto\n%f %f lineto\n", x * X_SKALA + LEFTMARGIN,
                ymin * Y_SKALA + Y_ORIGO, x * X_SKALA + LEFTMARGIN, ymax
                        * Y_SKALA + Y_ORIGO);
        for (i = ymin; i <= ymax; i += strecktathet) {
            fprintf(psfp, "%f %f moveto\n%f %f lineto\n", x * X_SKALA
                    + LEFTMARGIN, i * Y_SKALA + Y_ORIGO, (x + strecklangd)
                    * X_SKALA + LEFTMARGIN, i * Y_SKALA + Y_ORIGO);
            fprintf(psfp,
                    "%f %f moveto\n%d 3 div neg 0 exch rmoveto (%3.1f) show\n",
                    label_col, i * Y_SKALA + Y_ORIGO, NUMBERSIZE, i);

            /*
             * fprintf(psfp,
             * "char true charpath flattenpath pathbbox /yur exch def pop yur add 2 div 0 exch moveto\n"
             * ); fprintf(psfp,"char show grestore\n");
             */

        }
        fprintf(psfp, "stroke\n");
    }

    /* char stringwidth 2 div neg 0 moveto char show restore */

    private void fprintf(StringBuffer fp2, String string) {
        fp2.append(string);
    }

    private void fprintf(StringBuffer fp2, String string, Object... args) {
        fprintf(fp2, String.format(string, args));
    }
}
