package robertfera.mad.bu.edu.bumad_2016_robertfera;

import java.util.ArrayList;

/**
 * Created by rfera213 on 12/8/16.
 */

public interface DataPasser {
    public ArrayList<?> ParseJSON(String json);
    public void postFetch(ArrayList<?> data);
}
