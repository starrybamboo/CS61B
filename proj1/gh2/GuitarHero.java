//package gh2;
//import edu.princeton.cs.algs4.StdAudio;
//import edu.princeton.cs.algs4.StdDraw;

package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */

        String field = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] tmpString = new GuitarString[37];
        double[] a = new double[37];
        for (int i = 0; i < 37; i++) {
            a[i] = 440 * Math.pow(2, (i - 24) / 12);
            tmpString[i] = new GuitarString(a[i]);
        }


        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int m = field.indexOf(key);
                if (m == -1) {
                    continue;
                }
                tmpString[m].pluck();
            }

            double sample = 0;
            for (int i = 0; i < field.length(); i++) {
                sample += tmpString[i].sample();
            }
            StdAudio.play(sample);

            for (int i = 0; i < field.length(); i++) {
                tmpString[i].tic();
            }
        }
    }
}




//public class GuitarHero {
//
//    public static void main(String[] args) {
//        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
//
//        GuitarString[] guitarStrings = new GuitarString[keyboard.length()];
//        for (int i = 0; i < keyboard.length(); i++) {
//            double frequency = 440 * Math.pow(2, (i - 24) / 12.0);
//            guitarStrings[i] = new GuitarString(frequency);
//        }
//        /* create two guitar strings, for concert A and C */
//
//        while (true) {
//
//            /* check if the user has typed a key; if so, process it */
//            if (StdDraw.hasNextKeyTyped()) {
//                char key = StdDraw.nextKeyTyped();
//                int index = keyboard.indexOf(key);
//                if (index != -1) {
//                    guitarStrings[index].pluck();
//                }
//            }
//
//            /* compute the superposition of samples */
//            double sample = 0;
//            for (int i = 0; i < keyboard.length(); i++) {
//                sample += guitarStrings[i].sample();
//            }
//
//            /* play the sample on standard audio */
//            StdAudio.play(sample);
//
//            /* advance the simulation of each guitar string by one step */
//            for (int i = 0; i < keyboard.length(); i++) {
//                guitarStrings[i].tic();
//            }
//        }
//    }
//}