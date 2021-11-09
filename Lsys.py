import turtle as t
import random as r
from math import pi, sin, cos, sqrt, acos

def processed(s, rules):
    j = 0
    while j < len(s):
        current = rules.get(s[j])
        if not current:
            current = s[j]
        s = s[:j] + current + s[j+1:]
        j = j + len(current)
    return s

def serpin():
    s = 'a'
    rules = {'a': 'b-a-b', 'b': 'a+b+a'}
    for i in range(8):
        s = processed(s, rules)
    for j in s:
        if j == 'a' or j == 'b':
            t.forward(1)
        elif j == '+':
            t.right(60)
        else:
            t.left(60)

def dragon():
    s = 'fx'
    rules = {'x': 'x+yf', 'y': 'fx-y'}
    for i in range(15):
        s = processed(s, rules)
    for j in s:
        if j == 'f':
            t.forward(1)
        elif j == '+':
            t.right(90)
        else:
            t.left(90)

if __name__ == "__main__":
    t.delay(0)
    t.up()
    t.goto(-400, 200)
    t.down()
    serpin()
    t.up()
    t.goto(100, -100)
    t.down()
    dragon()
    t.mainloop()
