import turtle as t
import random as r
from math import pi, sin, cos, sqrt, acos

arrNums = []
arrSquares = []

def swap(i):
    arrNums[i],arrNums[i+1] = arrNums[i+1],arrNums[i]
    arrSquares[i].forward(30)
    arrSquares[i+1].backward(30)
    arrSquares[i],arrSquares[i+1] = arrSquares[i+1],arrSquares[i]

def sort():
    for i in range(len(arrNums)):
        for j in range(len(arrNums)-1):
            if arrNums[j] > arrNums[j+1]:
                swap(j)

def drawTriangle(xA,yA,xB,yB,xC,yC, color):
    t.begin_fill()
    t.color(color)
    t.up()
    t.goto(xA,yA)
    t.down()
    t.goto(xB,yB)
    t.goto(xC,yC)
    t.up()
    t.end_fill()

def serpinsky(xA,yA,xB,yB,xC,yC, deep):
    if (deep > 7):
        return
    drawTriangle(xA,yA,xB,yB,xC,yC, "black")
    drawTriangle((xA+xB)/2, (yA+yB)/2, (xA+xC)/2, (yA+yC)/2, (xC+xB)/2, (yC+yB)/2, "white")
    serpinsky((xA+xB)/2, (yA+yB)/2, xB, yB, (xC+xB)/2, (yC+yB)/2, deep+1)
    serpinsky(xA, yA,  (xA+xB)/2, (yA+yB)/2, (xA+xC)/2, (yA+yC)/2, deep+1)
    serpinsky((xA+xC)/2, (yA+yC)/2, (xC+xB)/2, (yC+yB)/2, xC, yC, deep+1)

if __name__ == "__main__":
    #arrNums = [15, 1, -6, 7, 12, -3, 6, 9]
    #t.shape("square")
    #t.up()
    #for i in range(len(arrNums)):
    #    a = t.clone()
    #    a.turtlesize(abs(arrNums[i]), 1)
    #    a.forward(i*30)
    #    a.left(90)
    #    a.forward(10*arrNums[i] - 20)
    #    a.right(90)
    #    arrSquares.append(a)
    #t.hideturtle()
    #sort()
    #t.mainloop()
    #drawTriangle(0,0,0,100,100,0, "black")
    serpinsky(0,0,50,0,50,50*sqrt(3), 0)
    t.mainloop()
