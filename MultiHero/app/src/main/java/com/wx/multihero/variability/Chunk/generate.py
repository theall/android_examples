# generate box and effect from array, bilge theall, all rights giveup
import os, sys
import math
import json
import uuid
import shutil
import time
import re

g_effectList = [
    {'TypeName':'TEST', 'ClassName':'Test', 'frames':[[3,1,5],[3,1,5],[3,1,4]]},
    {'TypeName':'BLOCKING', 'ClassName':'Blocking', 'frames':[[3,1,5],[1,1,5],[1,2,4]]},
    #{'TypeName':'ROUND_INTRODUCTION', 'ClassName':'RoundIntroduction', 'frames':[[2,1,100,(30,'ready.mp3'),(0,300)],[2,2,140,(80,'fight.mp3',NoUserInput=0),(0,-600)]]},
    {'TypeName':'RYU_BLUE_BALL_IMPACT', 'ClassName':'RyuBlueBallImpact', 'frames':[[3,1,10],[3,2,10]]},
    {'TypeName':'EXPLOSION_40', 'ClassName':'Explosion40', 'frames':[[4,1,5],[4,2,5],[4,1,5],[4,2,5]]},
    {'TypeName':'WHITE_STAR_HIT', 'ClassName':'WhiteStarHit', 'frames':[[5,1,5],[5,2,5],[5,3,5]]},
    {'TypeName':'WEB_SHOT_IMPACT','ClassName':'WebShotImpact','frames':[[6,1,10],[6,2,10]]},
    {'TypeName':'FIRE_BALL_IMPACT','ClassName':'FireBallImpact','frames':[[7,1,10],[7,2,10]]},
    {'TypeName':'COINS','ClassName':'Coins','frames':[[8,1,3],[8,2,3],[8,3,3]]},
    {'TypeName':'LAVA_ROCK_BREAKING','ClassName':'LavaRockBreaking','frames':[[9,1,10],[9,2,7]]},
    {'TypeName':'M_VS_C_HIT','ClassName':'MVSCHit','frames':[[10,1,3],[10,2,3],[10,3,3]]},
    {'TypeName':'VULCANO_EXPLOSION','ClassName':'VulcanoExplosion','frames':[[11,1,10],[11,2,25]]},
    {'TypeName':'AIR_TRAIL_GOING_UP','ClassName':'AirTrailGoingUp','frames':[[1,2,14,(0,-1)]]},
    {'TypeName':'BRIGHT_DOT','ClassName':'BrightDot','frames':[[13,1,7]]},
    {'TypeName':'BLOOD','ClassName':'Blood','frames':[[14,1,6],[14,2,6]]},
    {'TypeName':'GREEN_PICK_UP_SIGN','ClassName':'GreenPickUpSign','frames':[[15,1,5],[15,2,5],[15,3,4]]},
    {'TypeName':'SMOKE','ClassName':'Smoke','frames':[[16,1,5],[16,2,5],[16,3,4]]},
    {'TypeName':'RED_RAY_IMPACT','ClassName':'RedRayImpact','frames':[[17,1,1],[17,2,2]]},
    {'TypeName':'BLUERAY','ClassName':'Blueray','frames':[[18,1,1]]},
    {'TypeName':'BLUERAY_2','ClassName':'Blueray2','frames':[[19,1,1]]},
    {'TypeName':'RASH_HIT','ClassName':'RashHit','frames':[[20,1,5],[20,2,5],[20,3,5]]},
    {'TypeName':'BLUERAY_IMPACT','ClassName':'BluerayImpact','frames':[[21,1,1]]},
    {'TypeName':'BLUERAY_IMPACT_2','ClassName':'BluerayImpact2','frames':[[22,1,1]]},
    {'TypeName':'POWER_BALL','ClassName':'PowerBall','frames':[[23,1,1]]},
    {'TypeName':'FIRE_GOING_UP','ClassName':'FireGoingUp','frames':[[24,1,2,(0,-1)],[24,2,3]]},
    {'TypeName':'4_WAY_EXPLOSION','ClassName':'4WayExplosion','frames':[[25,1,2],[25,2,3],[25,3,4],[25,4,3]]},
    {'TypeName':'BATMAN_BOMB_SMOKE','ClassName':'BatmanBombSmoke','frames':[[26,1,4],[26,2,3],[26,3,4],[26,4,3]]},
    {'TypeName':'GREEN_RAY_IMPACT','ClassName':'GreenRayImpact','frames':[[27,1,8],[27,2,8]]},
    {'TypeName':'LITTLE_SMOKE','ClassName':'LittleSmoke','frames':[[28,1,5],[28,2,5],[28,3,5]]},
    {'TypeName':'LITTLE_SMOKE_GOING_UP','ClassName':'LittleSmokeGoingUp','frames':[[28,1,5,(0,-1)],[28,2,5],[28,3,5,(0,1)]]},
    {'TypeName':'RAY_BALL_IMPACT','ClassName':'RayBallImpact','frames':[[29,1,5],[29,2,5],[29,3,5]]},
    {'TypeName':'ELECTRICITY','ClassName':'Electricity','frames':[[30,1,4],[30,2,4],[30,3,4]]},
    {'TypeName':'RED_RAY','ClassName':'RedRay','frames':[[31,1,1]]},
    {'TypeName':'RED_RAY_2','ClassName':'RedRay2','frames':[[32,1,1]]},
    {'TypeName':'BIG_ROCK_BREAKING','ClassName':'BigRockBreaking','frames':[[33,1,6],[33,2,6]]},
    {'TypeName':'LITTLE_ROCK_BREAKING','ClassName':'LittleRockBreaking','frames':[[34,1,6],[34,2,6]]},
    {'TypeName':'WATER_SPLASH','ClassName':'WaterSplash','frames':[[35,1,5],[35,2,5],[35,3,5],[35,4,5],[35,5,5],[35,6,5]]},
    {'TypeName':'BIG_WEB','ClassName':'BigWeb','frames':[[69,1,5],[69,2,5],[69,3,10]]},
    {'TypeName':'YELLOW_RAY','ClassName':'YellowRay','frames':[[36,1,1]]},
    {'TypeName':'YELLOW_RAY_2','ClassName':'YellowRay2','frames':[[37,1,1]]},
    {'TypeName':'TMNT_HIT','ClassName':'TmntHit','frames':[[50,1,7],[50,2,8]]},
    {'TypeName':'YELLOW_RAY_IMPACT','ClassName':'YellowRayImpact','frames':[[38,1,1]]},
    {'TypeName':'YELLOW_RAY_IMPACT_2','ClassName':'YellowRayImpact2','frames':[[39,1,1]]},
    {'TypeName':'DRAGON_BALL','ClassName':'DragonBall','frames':[[51,1,6],[51,2,6],[51,3,6],[51,4,6],[51,5,6],[51,6,6]]},
    {'TypeName':'JIMMY_ATTACK','ClassName':'JimmyAttack','frames':[[52,1,1],[52,2,1],[52,3,1],[52,4,1],[52,5,1],[52,6,1],[52,7,1],[52,8,1],[52,9,1],[52,10,1],[52,11,1],[52,12,1],[52,13,1],[52,14,1],[52,15,1],[52,16,1],[52,17,1],[52,18,1],[52,19,1]]},
    {'TypeName':'KNIFE','ClassName':'Knife','frames':[[53,1,1],[53,2,1],[53,3,1],[53,4,1],[53,5,1],[53,6,1]]},
    {'TypeName':'DRAGON_BALL_DOUBLE','ClassName':'DragonBallDouble','frames':[[54,1,5],[54,2,5],[54,3,5],[54,4,5],[54,5,5],[54,6,5],[54,7,5]]}
]

"""package com.wx.multihero.variability.Chunk;
    public class Blocking extends Chunk {
        public Blocking() {
            super(Type.BLOCKING);

            add(5, 3, 1);
            add(5, 1, 1);
            add(4, 1, 2);
        }
    }
    """
def generateClass(className, typeName, addStr):
    tpl = """package com.wx.multihero.variability.Chunk;
    
public class %s extends Chunk {
    public %s() {
        super(Type.%s);

        %s
    }
}"""
    return tpl%(className, className, typeName, addStr)
    
def getAddStr(l):
    return "add(%d, %d, %d);\n"%(l[2],l[0],l[1])
    
def saveClass(j, fileName):
    basename, ext = os.path.splitext(fileName)
    if ext.lower()!='java':
        fileName = basename + '.java'
    fp = open(fileName,'w')
    fp.write(j)
    fp.close()

def export(file):
    fileName = file['ClassName']
    print("Generating " + fileName)
    addList = []
    for f in file['frames']:
        addList.append(getAddStr(f))
    classString = generateClass(fileName, file['TypeName'], "        ".join(addList))
    saveClass(classString, fileName)

def generate():
    for effect in g_effectList:
        export(effect)

if __name__ == '__main__':
    generate()
