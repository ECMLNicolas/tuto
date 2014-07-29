package Morpion;

import java.util.Scanner;
import java.util.Random;

class Joueur
{

    // le pseudo du joueur, c'est mieux
    public String pseudo;
    // son symbole
    public String symbole;
    // son score
    public int score ;
}

class Grille
{
    // la belle grille
    public int[][] grille ;
    // les joueurs
    public Joueur[] joueurs ;
    // le joueur actif
    public int actif ;

    /* On initialise le jeu */
    public void init()
    {
        grille = new int[3][3] ; /* grille vide */
        joueurs = new Joueur[2] ; /* 2 joueurs */
        for (int i = 0 ; i < 2 ; i++)
        {
            Scanner sc = new Scanner(System.in);
            System.out.print("Veuillez saisir le pseudo du joueur " + (i + 1)/* + " : "*/);
            if (i == 0)
            {
                System.out.print(" (X) : ");
            }
            else
            {
                System.out.print(" (O) : ");
            }
            String pseudo = (String)sc.next(); /* On leur demande leur pseudo */
            joueurs[i] = new Joueur(); /* On créé un nouveau joueur */
            joueurs[i].pseudo = pseudo ; /* on lui donne son pseudo */
//            System.out.print("Veuillez choisir votre symbole : ");
//            String symbole = (String)sc.next(); /* On pourrait leur demander leur symbole (mais on doit changer un peu de code) */
//            joueurs[i].symbole = symbole ;
        }
        Random rand = new Random();
        actif = rand.nextInt(2); /* On fait commencer un des deux joueurs au hasard, et pour chaque nouvelle partie, on alterne */
        System.out.println();
        System.out.println("C'est " + joueurs[actif].pseudo + " qui commence !");
        System.out.println();
    }

    public void restart()
    {
        grille = new int[3][3] ; /* On réinitialise la grille */
    }

    public void afficheGrille()
    {
        System.out.println();
        System.out.println("     1     2     3");
        System.out.println("  -------------------");
        for (int i = 0 ; i < grille.length ; i++)
        {
            System.out.print((i+1) + " |");
            for (int j = 0 ; j < grille[i].length ; j++)
            {
                if (grille[i][j] == 1)
                {
                    System.out.print("  X  "/*"  " + joueurs[0].symbole + "  "*/); /* à modifier si on veut utiliser des symboles personnalisés : "  " + joueurs[0].symbole + "  " */
                }
                else if (grille[i][j] == 2)
                {
                    System.out.print("  O  "/*"  " + joueurs[1].symbole + "  "*/); /* de même :  : "  " + joueurs[1].symbole + "  " */
                }
                else
                {
                    System.out.print("     ");
                }
                System.out.print("|");
            }
            System.out.println();
            System.out.println("  -------------------");
        }
        System.out.println();
    }
    
    public int choisirLigne()
    {
        Scanner sc = new Scanner(System.in);
        int ligne = 0 ;
        boolean valide = false ;
        while (!valide) /* tant que le joueur n'a pas choisi une ligne valide */
        {
            System.out.print("Veuillez saisir le numéro de la ligne dans laquelle vous voulez jouer : ");
            ligne = sc.nextInt() - 1 ; /* on lui demande dans quelle ligne il veut jouer */
                                        /* attention aux décalages entre les humains et les tableaux */
            if (0 <= ligne && ligne <= 2) /* il faut faire attention à si la ligne est une ligne qui existe */
            {
                valide = true ; /* ligne valide */
            }
            else
            {
                System.out.println("Veuillez choisir une autre valeur SVP !");
            }
        }
        return ligne ;
    }
    
    public int choisirColonne()
    {
        Scanner sc = new Scanner(System.in);
        int colonne = 0 ;
        boolean valide = false ;
        while (!valide) /* tant que le joueur n'a pas choisi une colonne valide */
        {
            System.out.print("Veuillez maintenant saisir le numéro de la colonne dans laquelle vous voulez jouer : ");
            colonne = (int)sc.nextInt() - 1 ;   /* on lui demande dans quelle colonne il veut jouer */
                                                /* attention aux décalages entre les humains et les tableaux */
            if (0 <= colonne && colonne <= 2) /* il faut faire attention à si la colonne est une colonne qui existe */
            {
                valide = true ; /* colonne valide */
            }
            else
            {
                System.out.println("Veuillez choisir une autre valeur SVP !");
            }
        }
        return colonne ;
    }
    
    public void play()
    {
        System.out.println("C'est à " + joueurs[actif].pseudo + " de jouer !");
        System.out.println();
        boolean stop = false ;
        int ligne ;
        int colonne ;
        while (!stop)
        {
            ligne = choisirLigne() ; /* on choisit la ligne */
            colonne = choisirColonne() ;  /* on choisit la colonne */
            if (grille[ligne][colonne] == 0) /* si la case est vide */
            {   /* alors on joue là */
                grille[ligne][colonne] = actif + 1 ; /* attention aux décalages entre les humains et les tableaux */
                stop = true ;
            }
            else
            {
                System.out.println("Vous ne pouvez pas jouer ici, tricheur !"); /* Et non, ça ne marchera pas ! */
            }
        }
    }

    public boolean ligneComplete(int i)
    {
        return ( (grille[i][0] != 0) && (grille[i][0] == grille [i][1]) && (grille[i][1] == grille [i][2]) );
    }

    public boolean colonneComplete(int i)
    {
        return ( (grille[0][i] != 0) && (grille[0][i] == grille [1][i]) && (grille[1][i] == grille [2][i]) );
    }

    public boolean diagonaleComplete()
    {
        return ( ( (grille[1][1] != 0) && ( ( (grille[0][0] == grille [1][1]) && (grille[1][1] == grille[2][2]) ) || ( (grille[0][2] == grille [1][1]) && (grille[1][1] == grille[2][0]) ) ) ) ) ;
    }

    public boolean victoire()
    {
        for (int i = 0 ; i < 3 ; i++)
        {
            if (ligneComplete(i) || colonneComplete(i))
            {
                return true ;
            }
        }
        if (diagonaleComplete())
        {
            return true ;
        }
        return false ;
    }

    public boolean matchNul(boolean win)
    {
        for (int i = 0 ; i < grille.length ; i++)
        {
            for (int j = 0 ; j < grille[i].length ; j++)
            {
                if (grille[i][j] == 0)
                {
                    return false ;
                }
            }
        }
        if (!win)
        {
            System.out.println("Match nul, dommage !");
            return true ;
        }
        return false ;
    }
    
    public void afficheScore()
    {
        System.out.println();
        System.out.println("Voici les scores :");
        System.out.println();
        for (int i = 0 ; i < 2 ; i++)
        {
            System.out.println(joueurs[i].pseudo + " a gagné " + joueurs[i].score + " parties.");
        }
        System.out.println();
    }

    public boolean stop()
    {
        boolean res = false ;
        String tmp = "" ;
        while(!tmp.equals("O") && !tmp.equals("N")){
            System.out.println("Voulez vous jouer un nouveau match ? (O ou N)");
            Scanner sc = new Scanner(System.in);
            tmp = sc.next();
            if(!tmp.equals("O") && !tmp.equals("N"))
                System.out.println("REPONSE INVALIDE !!!");
            else if(tmp.equals("N"))
                res = true;
            }
        return res ;
        }

    public void run()
    {
        init(); /* On initialise */
        boolean nul ;
        boolean win ;
        do
        {
            restart() ; /* superflu pour le premier tour mais utile pour au cas où les joueurs veulent jouer une nouvelle partie */
            afficheGrille() ;
            win = victoire() ;
            nul = matchNul(win) ;
            while (!win && !nul) /* tant qu'il n'y a pas de victoires ni de match nul */
            {
                play(); /* On fait jouer un joueur */
                afficheGrille(); /* On montre le résultat de son choix */
                win = victoire() ; /* on regarde si son choix lui a permis de gagner */
                if (win)
                {
                    joueurs[actif].score++;
                    System.out.println("Bravo à " + joueurs[actif].pseudo + " !");
                }
                nul = matchNul(win) ; /* on regarde s'il n'y pas match nul */
                actif = (actif + 1) % 2 ; /* on change de joueur */
            }
            afficheScore(); /* on affiche les scores */
        }
        while (!stop()) ; /* tant que les joueurs veulent continuer à jouer de nouvelles parties, on recommence */
    }
}
    

public class Morpion
{
    public static void main(String[] args)
    {
        Grille jeu = new Grille();
        jeu.run();
    }

}
