package Game;

import Game.Object.Item;

import java.util.ArrayList;

public class ItemObject {
    private double x;
    private double y;
    private Item item;

    public ItemObject(double x, double y, Item item) {
        this.x  = x;
        this.y = y;
        this.item = item;
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public Item getItem() {
        return item;
    }

    public static ArrayList<ItemObject> generateItemOnFild(int maxX, int maxY) {
        ArrayList<ItemObject> generationItem = new ArrayList<ItemObject>();

        //아이템 생상 갯수
        int gunAmount = 5;
        int powerAmount = 5;
        int speedAmount = 5;
        int healthAmount = 5;
        int bulletAmount = 5;

        boolean isThere;
        double temX;
        double temY;

        for(int i = 0 ; i < gunAmount; i++) {
            while(true) {
                //랜덤 좌표 생성
                temX = Math.random()*maxX;
                temY = Math.random()*maxY;

                //중복검사
                isThere = false;
                for(int j = 0 ; j < generationItem.size(); j++) {
                    if (generationItem.get(i).x ==temX && generationItem.get(i).y == temY) {
                        isThere = true;
                    }
                }
                if(!isThere) {
                    break;
                }
            }
            generationItem.add(new ItemObject(temX,temY, Item.GUN));
        }

        for(int i = 0 ; i < powerAmount; i++) {
            while(true) {
                //랜덤 좌표 생성
                temX = Math.random()*maxX;
                temY = Math.random()*maxY;

                //중복검사
                isThere = false;
                for(int j = 0 ; j < generationItem.size(); j++) {
                    if (generationItem.get(i).x ==temX && generationItem.get(i).y == temY) {
                        isThere = true;
                    }
                }
                if(!isThere) {
                    break;
                }
            }
            generationItem.add(new ItemObject(temX,temY, Item.POWER));
        }

        for(int i = 0 ; i < speedAmount; i++) {
            while(true) {
                //랜덤 좌표 생성
                temX = Math.random()*maxX;
                temY = Math.random()*maxY;

                //중복검사
                isThere = false;
                for(int j = 0 ; j < generationItem.size(); j++) {
                    if (generationItem.get(i).x ==temX && generationItem.get(i).y == temY) {
                        isThere = true;
                    }
                }
                if(!isThere) {
                    break;
                }
            }
            generationItem.add(new ItemObject(temX,temY, Item.SPEED));
        }

        for(int i = 0 ; i < healthAmount; i++) {
            while(true) {
                //랜덤 좌표 생성
                temX = Math.random()*maxX;
                temY = Math.random()*maxY;

                //중복검사
                isThere = false;
                for(int j = 0 ; j < generationItem.size(); j++) {
                    if (generationItem.get(i).x ==temX && generationItem.get(i).y == temY) {
                        isThere = true;
                    }
                }
                if(!isThere) {
                    break;
                }
            }
            generationItem.add(new ItemObject(temX,temY, Item.HEALTH));
        }

        for(int i = 0 ; i < bulletAmount; i++) {
            while(true) {
                //랜덤 좌표 생성
                temX = Math.random()*maxX;
                temY = Math.random()*maxY;

                //중복검사
                isThere = false;
                for(int j = 0 ; j < generationItem.size(); j++) {
                    if (generationItem.get(i).x ==temX && generationItem.get(i).y == temY) {
                        isThere = true;
                    }
                }
                if(!isThere) {
                    break;
                }
            }
            generationItem.add(new ItemObject(temX,temY, Item.BULLET));
        }
        return generationItem;
    }
}
