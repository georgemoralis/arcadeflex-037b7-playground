/*
 * ported to v0.37b7
 *
 */
package arcadeflex.v037b7.sound;

public class samplesH {

    public static class Samplesinterface {

        public Samplesinterface(int chan, int vol, String[] names) {
            channels = chan;
            volume = vol;
            samplenames = names;
        }

        public int channels;/* number of discrete audio channels needed */
        public int volume;/* global volume for all samples */
        public String[] samplenames;
    }
}
