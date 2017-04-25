#!/usr/bin/python3

import math
# Her foreslår jeg å representere punkter og vektorer med 2-tupler, altså par av tall.
# For å kunne teste løsningen med den angitte main-metoden, er man nødt til å
# følge det valget.

# MULIG NYTTIG HJELPEMETODEA?
def forflytning(A,B):
    return tuple((b-a for a,b in zip(A,B)))

def trekantAreal(A,B,C):
    """Returnerer det orienterte arealet av trekanten A,B,C.
    Forutsetter at A,B,C er lister eller tupler med 2 komponenter.
    """
    AB = forflytning(A,B);
    AC = forflytning(A,C);

    return (AB[0]*AC[1] - AB[1]*AC[0]) / 2

def areal(punkter):
    """Returnerer det orienterte arealet av polygonet representert
    av parameteren `punkter`. `punkter` er en liste som inneholder
    punktene som utgjør polygonet. 
    """
    if (len(punkter) < 3): 
        return 0 # Hvis polygonet har færre enn 3 kanter, kan det ikke omslutte et areal
    # TODO: Fyll inn det som mangler.
    
    return sum(trekantAreal(punkter[0],i,j) for (i,j) in zip(punkter[1:-1], punkter[2:]))

def polyvinkler(n):
    return [2*pi/n*i for i in range(n)]

def indrePolygon(n):
    #vinkler = (2*pi/n*i for i in range(n))
    vinkler = polyvinkler(n)
    return [(cos(v), sin(v)) for v in vinkler]

def ytrePolygon(n):
    vinkler = polyvinkler(n)
    halfAngle = vinkler[1]/2
    #print("halfangle = ", halfAngle)

    return [(cos(v)/cos(halfAngle),sin(v)/cos(halfAngle)) for v in vinkler]

def solvePi(n):
    ## corner angle == 360* / n
    precision = n

    theta = corner / 2
    centerTheta = 180 - corner
    
    return 0

def main():
    A,B,C = (-1,-1), (1,-1),(0,1)  # Fin test-case: Har areal 2

    print("Beregnet areal av trekant[",A,B,C,"] = ",trekantAreal(A,B,C))
    print("Forventet resultat: 2")

    enhetsKvadrat = [(0,0),(1,0),(1,1),(0,1)] # Fin test-case: Har areal 1
    print("Beregnet areal av kvadrat",enhetsKvadrat," = ",areal(enhetsKvadrat) )
    print("Forventet resultat: 1")

    polygon = [(0,0),(4,0),(4,1),(2,2),(0,1)] # Test case: areal = 6
    positiveArea = areal(polygon)
    print("Beregnet areal av polygon",polygon," = ",positiveArea )
    print("Forventet resultat: 6")

    polygon.reverse()
    print("Area of the reversed polygone",polygon," = ", areal(polygon))
    print("Expected result: -{} <- the negative of the initial area".format(positiveArea))
    print()
    print("area of a triangle: ", areal(indrePolygon(3)))
    print("area of a square: ", areal(indrePolygon(4)))
    print("area of a hexagone: ", areal(indrePolygon(6)))
    print("area of a manytagone: ", areal(indrePolygon(100)))
    print("area of an ohmygone: ", areal(indrePolygon(1000)))
    print()
    print("area of an outer triangle: ", areal(ytrePolygon(3)))
    print("area of an outer square: ", areal(ytrePolygon(4)))
    print("area of an outer hexagone: ", areal(ytrePolygon(6)))
    print("area of an outer manytagone: ", areal(ytrePolygon(100)))
    print("area of an outer ohmygone: ", areal(ytrePolygon(1000)))

if __name__=='__main__':
    main()
