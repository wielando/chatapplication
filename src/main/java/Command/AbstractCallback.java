package Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractCallback {

    private final List<String> paramValues = new ArrayList<String>();

    private final List<String> requiredParamList = new ArrayList<String>();

    protected final HashMap<String, String> params = new HashMap<String, String>();

    protected boolean isCallable = false;

    public AbstractCallback(CommandListener commandListener, ArrayList<String> paramValues, List<String> requiredParamList) {

        if (!requiredParamList.isEmpty() && !paramValues.isEmpty()) {

            this.setParamList(requiredParamList);
            this.setParamValues(paramValues);

            Integer requiredParamListSize = this.requiredParamList.size();
            Integer paramValuesSize = this.paramValues.size();

            if (requiredParamListSize.equals(paramValuesSize)) {
                for (int requiredParamListKey = 0; requiredParamListKey < this.requiredParamList.size(); requiredParamListKey++) {
                    this.params.put(this.requiredParamList.get(requiredParamListKey), this.paramValues.get(requiredParamListKey));
                }

                this.setIsCallable();
            }
        }

        if (requiredParamList.isEmpty()) {
            this.setIsCallable();
        }
    }

    private void setIsCallable() {
        this.isCallable = true;
    }

    private void setParamValues(List<String> paramValues) {
        this.paramValues.addAll(paramValues);
    }

    private void setParamList(List<String> params) {
        this.requiredParamList.addAll(params);
    }

}
