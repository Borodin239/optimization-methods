package lab3;

import org.la4j.Vector;
import org.la4j.vector.dense.BasicVector;

/**
 * Matrix interface.
 * @author Egor Malko
 * @version 1.0
 */
public interface Matrix {

    /**
     * Return element by row and column indexes.
     * @param i row index.
     * @param j column index.
     * @return element with specified indexes.
     */
    double get(int i, int j);

    /**
     * Changes the value of the selected element to the passed one.
     * @param i row index.
     * @param j column index.
     * @param val the value that we want to set for the matrix element.
     */
    void set(int i, int j, double val);

    /**
     * Returns size of the matrix.
     * @return matrix size.
     */
    int size();

    /**
     * Default multiplying vector on matrix.
     * @return resulting vector.
     */
    default Vector mulOnVec(Vector vec) {
        double[] res = new double[size()];
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                res[i] += get(i, j) * vec.get(j) ;
            }
        }
        return new BasicVector(res);
    }
}
