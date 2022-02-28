package arcadeflex.common;

//common imports
import static arcadeflex.common.ptrLib.*;

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
        
        public UShortArray(UBytePtr cp) {
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
        
        public UShortArray(int[] buffer) {
            int _longo = buffer.length;
            char[] _buffer = new char[_longo];
            for (int _i = 0; _i < _longo; _i++) {
                _buffer[_i] = (char) buffer[_i];
            }
            this.memory = _buffer;
            this.offset = 0;
        }
        
        public UShortArray(int[] buffer, int offset) {
            int _longo = buffer.length;
            char[] _buffer = new char[_longo];
            for (int _i = 0; _i < _longo; _i++) {
                _buffer[_i] = (char) buffer[_i];
            }
            this.memory = _buffer;
            this.offset = offset;
        }

        public char read() {
            return memory[offset];
        }
        
        public char readinc() {
            char _iOUT = memory[offset];
            offset++;
            return _iOUT;
        }
        
        public void inc() {
            offset++;
        }
        
        public void inc(int b) {
            offset+=b;
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
        
        public IntSubArray(UBytePtr _obj) {
            int _longo=_obj.memory.length;
            this.buffer = new int[_longo];
            for (int _i=0 ; _i<_longo ; _i++)
              this.buffer[_i] = _obj.memory[_i];
            this.offset = _obj.offset;
        }
        
        public IntSubArray(UBytePtr _obj, int offset) {
            int _longo=_obj.memory.length;
            this.buffer = new int[_longo];
            for (int _i=0 ; _i<_longo ; _i++)
              this.buffer[_i] = _obj.memory[_i];
            this.offset = _obj.offset + offset;
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
        
        public int readinc() {
            return readinc(0);
        }
        
        public int readinc(int index) {
            int _i = buffer[index + offset];
            offset++;
            return _i;
        }

        public void write(int value) {
            buffer[offset] = value;
        }

        public void write(int index, int value) {
            buffer[index + offset] = value;
        }
        
        public void inc() {
            inc(0);
        }

        public void inc(int value) {
            offset++;
        }
    }
}
