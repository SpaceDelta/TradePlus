package com.trophonix.tradeplus.gui;

import com.trophonix.tradeplus.util.ItemFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuButton {

    private final MenuAction action;
    private final ItemFactory icon;
}
