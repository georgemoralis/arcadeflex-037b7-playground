package arcadeflex.common;

import gr.codebb.arcadeflex.common.PtrLib.UBytePtr;

/**
 *
 * @author shadow
 */
public class subArrays {

    public static class UShortArray {

        public char[] memory;
        public int offset;

        public UShortArray(int size) {
            memory = new char[size];
            offset = 0;
        }

        public UShortArray(char[] m) {
            memory = m;
            offset = 0;
        }

        public UShortArray(char[] m, int b) {
            memory = m;
            offset = b;
        }

        public UShortArray(UShortArray cp) {
            memory = cp.memory;
            offset = cp.offset;
        }

        public UShortArray(UShortArray cp, int b) {
            memory = cp.memory;
            offset = cp.offset + b;
        }

        public UShortArray(UBytePtr cp, int b) {
            memory = cp.memory;
            offset = cp.offset + b;
        }

        public char read() {
            return memory[offset];
        }

        public char read(int offs) {
            return memory[offs + offset];
        }

        public void write(int offs, int value) {
            memory[offset + offs] = (char) value;
        }

        public void writeinc(char value) {
            memory[offset++] = value;
        }
    }

    public static class IntSubArray {

        public int[] buffer;
        public int offset;

        public IntSubArray(int size) {
            this.buffer = new int[size];
            this.offset = 0;
        }

        public IntSubArray(int[] buffer) {
            this.buffer = buffer;
            this.offset = 0;
        }

        public IntSubArray(int[] buffer, int offset) {
            this.buffer = buffer;
            this.offset = offset;
        }

        public IntSubArray(char[] buffer, int offset) {
            int _longo = buffer.length;
            int[] _buffer = new int[_longo];
            for (int _i = 0; _i < _longo; _i++) {
                _buffer[_i] = buffer[_i];
            }
            this.buffer = _buffer;
            this.offset = offset;
        }

        public IntSubArray(IntSubArray subarray) {
            this.buffer = subarray.buffer;
            this.offset = subarray.offset;
        }

        public IntSubArray(IntSubArray subarray, int offset) {
            this.buffer = subarray.buffer;
            this.offset = subarray.offset + offset;
        }

        public int read() {
            return buffer[offset];
        }

        public int read(int index) {
            return buffer[index + offset];
        }

        public void write(int value) {
            buffer[offset] = value;
        }

        public void write(int index, int value) {
            buffer[index + offset] = value;
        }

        public void inc(int value) {
            offset++;
        }
    }
}
