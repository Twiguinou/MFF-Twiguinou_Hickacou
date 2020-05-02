package com.twihick.modularexplosions.util;

import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class ShapeUtil {

    public static VoxelShape rotateShape(VoxelShape shape, Direction direction) {
        double coord0 = shape.getStart(Direction.Axis.X);
        double coord1 = shape.getStart(Direction.Axis.Z);
        double coord2 = shape.getEnd(Direction.Axis.X);
        double coord3 = shape.getEnd(Direction.Axis.Z);
        switch(direction) {
            case SOUTH:
                double temp0 = coord0, temp1 = coord1;
                coord0 = 1.0F - coord2;
                coord1 = 1.0F - coord3;
                coord2 = 1.0F - temp0;
                coord3 = 1.0F - temp1;
                break;
            case WEST:
                double temp2 = coord0, temp3 = coord1, temp4 = coord2;
                coord0 = coord3;
                coord1 = 1.0F - temp2;
                coord2 = temp3;
                coord3 = 1.0F - temp4;
                break;
            case EAST:
                double temp5 = coord0;
                coord0 = 1.0F - coord1;
                coord1 = coord2;
                coord2 = 1.0F - coord3;
                coord3 = temp5;
                break;
            default:
                break;
        }
        return VoxelShapes.create(coord0, shape.getStart(Direction.Axis.Y), coord1, coord2, shape.getEnd(Direction.Axis.Y), coord3);
    }

}
