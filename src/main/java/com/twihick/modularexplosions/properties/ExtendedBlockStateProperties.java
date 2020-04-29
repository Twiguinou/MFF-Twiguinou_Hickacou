package com.twihick.modularexplosions.properties;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class ExtendedBlockStateProperties extends BlockStateProperties {

    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

}
