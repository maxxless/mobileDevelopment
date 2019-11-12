package com.leshchyshyn.mobileapp.main_group.locations;

import com.leshchyshyn.mobileapp.data.model.Location;
import com.leshchyshyn.mobileapp.main_group.IMainView;

public interface ILocationView extends IMainView {
    void showLocation(Location location);
}
