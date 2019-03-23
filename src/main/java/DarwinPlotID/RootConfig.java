package DarwinPlotID;


import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class RootConfig {

    @Setting("toggled")
    private List<Toggled> toggled = new ArrayList<>();

    public List<Toggled> getCategories() {
        return toggled;
    }
}
