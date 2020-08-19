package com.mygdx.game;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyTexturePacker
{
    public static void main(String[] args) throws Exception
    {
        TexturePacker.process("core/assets", "core/assets","Atlas");
    }
}
