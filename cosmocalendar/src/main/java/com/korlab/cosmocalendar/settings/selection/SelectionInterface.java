package com.korlab.cosmocalendar.settings.selection;

import com.korlab.cosmocalendar.utils.SelectionType;

public interface SelectionInterface {

    @SelectionType
    int getSelectionType();

    void setSelectionType(@SelectionType int selectionType);
}
