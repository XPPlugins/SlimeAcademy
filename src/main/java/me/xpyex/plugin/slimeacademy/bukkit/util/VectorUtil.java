package me.xpyex.plugin.slimeacademy.bukkit.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class VectorUtil {
    /**
     * 根据实体，以及给出的模长，生成一个指向实体面部朝向的向量
     *
     * @param entity 实体
     * @param length 模长
     * @return 指向实体朝向的向量
     */
    public static Vector getVectorFromYawAndPitch(Entity entity, double length) {
        return getVectorFromYawAndPitch(entity.getLocation(), length);
    }

    /**
     * 根据坐标，以及给出的模长，生成一个指向坐标朝向的向量
     *
     * @param loc    包含yaw和pitch的坐标
     * @param length 模长
     * @return 指向实体朝向的向量
     */
    public static Vector getVectorFromYawAndPitch(Location loc, double length) {
        var yaw = loc.getYaw();  //在x和z轴上的平面角度，范围0~360
        var pitch = loc.getPitch();  //在平面和y轴的夹角，范围-90~90
        return getVectorFromYawAndPitch(yaw, pitch, length);
    }

    /**
     * 根据yaw和pitch，生成一个指向指定方向的向量
     *
     * @param yaw    在x和z轴上的平面角度
     * @param pitch  和y轴的夹角
     * @param length 模长
     * @return 指向指定方向的向量
     */
    @NotNull
    public static Vector getVectorFromYawAndPitch(float yaw, float pitch, double length) {
        //R即向量模长length
        var r1 = length * Math.cos(Math.toRadians(pitch));  //由图知cos(pitch) = r1/R，所以r1 = R * cos(pitch)，r1为length这条线段在x、z面上投影的长度
        var vecX = r1 * Math.cos(Math.toRadians(yaw));  //向量的x轴长度，由图知cos(yaw) = x/r1，所以x = r1 * cos(yaw)
        var vecY = length * Math.sin(Math.toRadians(pitch));  //y = r1 * sin(pitch)
        var vecZ = r1 * Math.sin(Math.toRadians(yaw));  //z = r1 * sin(yaw)
        return new Vector(vecX, vecY, vecZ);
    }

    private static double square(double x) {
        return x * x;
    }

    /**
     * 复制原向量，设定长度
     * @param vector 原向量
     * @param length 新长度
     * @return 新向量
     */
    public static Vector newLengthVector(Vector vector, double length) {
        if (length == 0) return new Vector(0, 0, 0);
        if (vector.length() == 0) return vector;  //若原来向量是零向量，没有方向，则无法修改长度，返回它自身
        //设倍数为x，旧长度o×倍数x得到新长度length，即o·x = l
        //所以可得x = l/o
        return vector.clone().multiply(length / vector.length());
    }
}
