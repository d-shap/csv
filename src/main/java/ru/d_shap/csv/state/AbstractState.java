// //////////////////////////////
// This code is authored
// Dmitry Shapovalov
// //////////////////////////////
package ru.d_shap.csv.state;

/**
 * Base class for all state classes.
 *
 * @author Dmitry Shapovalov
 */
abstract class AbstractState {

    AbstractState() {
        super();
    }

    abstract AbstractState processInput(char ch, Result result);

}
