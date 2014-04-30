package iAPIEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by les on 29/04/14.
 */
public class ResultJSON {

    private List<Result> _results;

    public List<Result> get_results() {
        if(_results == null)
            _results = new ArrayList<Result>();
        return _results;
    }

    public void set_results(List<Result> _results) {
        if(_results == null)
            _results = new ArrayList<Result>();
        this._results = _results;
    }
}
