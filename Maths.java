package algo;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Maths extends JPanel implements ActionListener {

		protected Font  axisFont, rectFont, carrFont;			//ici on va declarer les axes pour l'interface

		protected Color boxColor  = new Color (25, 143, 103),	//on va integrer les couleur d'après le RGB 
						gridColor = new Color (78, 23, 211),
						diagColor = new Color (93, 192, 85),    
						fontColor = new Color (23, 187, 98),
						carrColor = new Color (162, 34, 19);

		protected int   nDigitP, nDigitQ, dSize = 60, m1, m2, lastCarry, iResult[], xDigits[], yDigits[], prodTL[][], prodBR[][];
		//ici on declare toute les variables necessaire
		
		public Maths (int p, int q, Font font) {
		    nDigitP = (int) Math.ceil (Math.log10 (p));			//le math.ceil va retourner la plus petite valeur qui est plus grande ou égal à l'argument 
		    xDigits = new int[nDigitP];
		    nDigitQ = (int) Math.ceil (Math.log10 (q)); 		//le  math.log10 quant à lui retourne la base de 10 logarithmique de la valeur de l'arguement.
		    yDigits = new int[nDigitQ];
		
		    prodTL = new int[nDigitP][nDigitQ];     			//ici on va declarer 2 tableau de la grandeur des 2 valeurs inserrer
		    prodBR = new int[nDigitP][nDigitQ];
		
		    m1 = p; m2 = q;                 					
		    int np = p, nq = q, size = font.getSize(); 			//cette partie est pour stocker les valeurs dans les tableaux
		
		    for (int i = 0 ; i < nDigitP ; i++) {
		        xDigits[i] = np % 10;							//ici on va casser les chiffres individuellement en utilisant les modulo
		        np /= 10;										//et on va aussi diviser les reponse par 10 SI la valeur restante est plus
		    }													//que 9
		    
		    for (int i = 0 ; i < nDigitQ ; i++) {
		        yDigits[i] = nq % 10;
		        nq /= 10;
		    }
		
		    for (int i = 0 ; i < nDigitP ; i++) {       		//Ici on va initialiser les valeur dans les cases en utilisant
		        for (int j = 0 ; j < nDigitQ ; j++) {			//des tableau multi dimensionelle pour pouvoir y mettre
		            int prod = xDigits[i] * yDigits[j];			//les valeurs dans les cases. Pour inserer la bonne valeur dans la
		            prodTL[i][j] = prod / 10;					//case on va premierement diviser par 10 et par la suite
		            prodBR[i][j] = prod % 10;					//faire le modulo de 10 pour avoir chaque chiffre individuellement
		        }
		    }
		
		    axisFont = font.deriveFont (Font.PLAIN, size+8.0f);		//on va definir la tailles des chiffres qui vont etre afficher
		    rectFont = font.deriveFont (Font.PLAIN, size+4.0f);
		    carrFont = font.deriveFont (Font.PLAIN);
		
		    setPreferredSize (new Dimension ((nDigitP+2)*dSize, (nDigitQ+2)*dSize));		//ici on va definir une taille par defaut pour l'affichage
		}

		public void paint (Graphics g) {							//dans cette fonction on va definir l'interface
		    int w = getWidth(), h = getHeight();
		    Graphics2D g2 = (Graphics2D) g;         				//Avec la classe 2D on peut creer des forme en diagonal
		    g2.setPaint (Color.white);								//on va "peindre" l'interface en blanc pour une meilleur visibilité
		    g2.fillRect (0,0,w,h);									//on va definir la taille des case grace a la fonction fillRect
		
		    int dx = (int) Math.round (w/(2.0+nDigitP)),    		//Avec le Math.rount on va definir les espaces entre les cases
		        dy = (int) Math.round (h/(2.0+nDigitQ));    		//et aussi l'espace entre les chiffres dans les cases 
		
		    g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //avec le setRenderingHint on donne la qualiter des lignes
		    g2.setRenderingHint (RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		    g2.setFont (axisFont);
		    FontMetrics fm = g2.getFontMetrics();					//avec la classe FontMetrics on va faire afficher les contour
		    for (int i = 0 ; i < nDigitP ; i++) {       			//des grilles sur l'axe y
		        int px = w - (i+1)*dx;
		        g2.setPaint (gridColor);							
		        
		        if (i > 0)
		            g2.drawLine (px, dy, px, h-dy);					//cette ligne va utiliser la fonction drawline pour dessiner les 
		        													//lignes horizontal des case avec les coordoner defini dans la boucle for 
		        													//et ensuite va utiliser la hauteur d'une case aussi comme reference 
		        
		        String str = /*i + */"" + xDigits[i];
		        int strw = fm.stringWidth (str);
		        g2.setPaint (fontColor);							//cette ligne va attribuer la couleur a dessiner les lignes des cases
		        g2.drawString (str, px-dx/2-strw/2, 4*dy/5);		//quant  a cette ligne elle va dessiner elle aussi les lignes des cases mais verticalement
		   }

		   for (int i = 0 ; i < nDigitQ ; i++) {       				//cette boucle for quant a elle va faire la meme chose que la precedente a la difference
		        int py = h - (i+1)*dy;								//qu'elle va dessiner les lignes en verticale, c'est a dire sur l'axe X.
		        g2.setPaint (gridColor);
		        if (i > 0)
		            g2.drawLine (dx, py, w-dx, py);
		        String str = /*i + */"" + yDigits[i];
		        int strw = fm.stringWidth (str);
		        g2.setPaint (fontColor);
		        g2.drawString (str, w-dx+2*dx/5-strw/2, py-dy/2+10);
		   }

		   g2.setFont (rectFont);
		   fm = g2.getFontMetrics();           
    
    
		   for (int i = 0 ; i < nDigitP ; i++) {					//cette boucle for va elle dessiner les lignes en diagonal pour la multiplication arab
		        for (int j = 0 ; j < nDigitQ ; j++) {
		            int px = w - (i+1)*dx;
		            int py = h - (j+1)*dy;
		
		            String strT = "" + prodTL[i][j];
		            int strw = fm.stringWidth (strT);
		            g2.drawString (strT, px-3*dx/4-strw/2, py-3*dy/4+5);
		
		            String strB = "" + prodBR[i][j];
		            strw = fm.stringWidth (strB);
		            g2.drawString (strB, px-dx/4-strw/2, py-dy/4+5);
		        }
		        
		   }

		   g2.setFont (axisFont);
		   fm = g2.getFontMetrics();
		   int carry = 0;
		   Vector cVector = new Vector(), iVector = new Vector();		//Ici au lieu d'utiliser un tableau normal on va utiliser un table dynamic *vector*
		   
		   for (int k = 0 ; k < 2*Math.max (nDigitP, nDigitQ) ; k++) {
		       int dSum = carry, i = k/2, j = k/2;						//ici on va declarer d'autre variable qui vont servir a definir les incrementation	
		       
		       	if ((k % 2) == 0) {             						
		           if (k/2 < nDigitP && k/2 < nDigitQ)
		               dSum += prodBR[k/2][k/2];						//si le chiffre ete un chiffre paire on fait la somme des case
		           														
			           for (int c = 0 ; c < k ; c++) {					//pour faire la somme de tout les chiffre il faut utiliser une autre
			               if (--i < 0)									//boucle for pour definir l'ordre dans lequel il faut faire l'addition
			                   break;									//en diagonal
			               if (i < nDigitP && j < nDigitQ)
			                   dSum += prodTL[i][j];
			               if (++j == nDigitQ)
			                   break;
			               if (i < nDigitP && j < nDigitQ)
			                   dSum += prodBR[i][j];
			           }
		           														
		           i =  k/2; j = k/2;
            
			            for (int c = 0 ; c < k ; c++) {
			                if (--j < 0)
			                    break;
			                if (i < nDigitP && j < nDigitQ)
			                    dSum += prodTL[i][j];
			                if (++i == nDigitP)
			                    break;
			                if (i < nDigitP && j < nDigitQ)
			                    dSum += prodBR[i][j];
			            }
		       }
			   else 													//ici si k est impair on va effectuer la meme chose
			   {                   
		            if (k/2 < nDigitP && k/2 < nDigitQ)					//que l'etape precedente mais a l'envers
		                dSum += prodTL[k/2][k/2];
		            													
			            for (int c = 0 ; c < k ; c++) {
			                if (++j == nDigitQ)
			                    break;
			                if (i < nDigitP && j < nDigitQ)
			                    dSum += prodBR[i][j];
			                if (--i < 0)
			                    break;
			                if (i < nDigitP && j < nDigitQ)
			                    dSum += prodTL[i][j];
			            }
			            
			        i =  k/2; j = k/2;
			        													
			            for (int c = 0 ; c < k ; c++) {
			                if (++i == nDigitP)
			                    break;
			                if (i < nDigitP && j < nDigitQ)
			                    dSum += prodBR[i][j];
			                if (--j < 0)
			                    break;
			                if (i < nDigitP && j < nDigitQ)
			                    dSum += prodTL[i][j];
			            }
	            }

		        int digit = dSum % 10;  carry = dSum / 10;						//on va recasser les chiffres addition dans chaque case pour eviter que
		        cVector.addElement (new Integer (carry));						//le chiffre depasse le nombre de 9 et si il depasse 9 on va utiliser la methode
		        iVector.addElement (new Integer (digit));						//utilisée avec les modulos et ensuite envoyer le reste dans le tableau dynamic carry et digit
		        String strD = "" + digit;
		        int strw = fm.stringWidth (strD);
		        if (k < nDigitP) {
		            int px = w - (k+1)*dx - 4*dx/5, py = h-dy + fm.getHeight();
		            g2.drawString (strD, px-strw/2, py);
		        } else {
		            int px = dx - 12, py = h - (k-nDigitP+1)*dy - dy/4;
		            g2.drawString (strD, px-strw/2, py+5);
		    }
        } 

    g2.setPaint (diagColor);
    for (int i = 0 ; i < nDigitP ; i++) {							//Les deux boucle for suivante vont etre utiliser pour dessiner les lignes en diagonal
        int xt = (i+1) * dx,
            yb = (i+2) * dy;
        g2.drawLine (xt, dy, 0, yb);
    }
    
    for (int i = 0 ; i < nDigitQ ; i++) {
        int xb = (i + nDigitP - nDigitQ) * dx,
            yr = (i+1) * dy;
        g2.drawLine (w-dx, yr, xb, h);
    }

    g2.setFont (carrFont);
    g2.setPaint (carrColor);
    fm = g2.getFontMetrics();
    for (int k = 0 ; k < 2*Math.max (nDigitP, nDigitQ) ; k++) {
        carry = ((Integer) cVector.elementAt (k)).intValue();
        lastCarry = carry;  										// pour l'affichage
        if (carry == 0)
            continue;
        String strC = "" + carry;
        int strw = fm.stringWidth (strC),
            px = w-dx-5-strw/2,         							// Const X pendant l'incrementation vertical
            py = dy + fm.getHeight();       						// Const Y pendant la decrementation horizontal
        if (k < (nDigitQ-1))
            py = h-(k+3)*dy + dy/5 + fm.getHeight();
        else
            px = w - (k-nDigitQ+2) * dx - dx/2 - strw/2;
        g2.drawString (strC, px, py);
    }

    int n = iVector.size();     									//Pour stocker la valeur a afficher
    iResult = new int[n];
    for (int i = 0 ; i < n ; i++)
        iResult[i] = ((Integer) iVector.elementAt (n-i-1)).intValue();
    g2.setPaint (boxColor);     g2.drawRect (dx, dy, w-2*dx, h-2*dy);
}


public void actionPerformed (ActionEvent e) {
    String cmd = e.getActionCommand();
   
}

public static void main (String[] args) {
    JTextField tf1 = new JTextField (), tf2 = new JTextField();		//ici on va declarer un textField que nous allons ajouter plutard dans l'interface
    JPanel num2m = new JPanel(new GridBagLayout());					//ici on declare simplement un panel qui va servir à afficher quel chiffre inserer
    GridBagConstraints gbc = new GridBagConstraints();				//une grille va etre declarer pour l'affichage des chiffres
    gbc.insets = new Insets (2,2,2,2);								//la fonction insets va mettre le minimum d'espace entre les composant dans la grille.

    gbc.fill = GridBagConstraints.HORIZONTAL;						//ici on indique comment la grille sera rempli
    gbc.gridx = 0;				
    gbc.gridy = GridBagConstraints.RELATIVE;						//ici on indique que la position de la grille sera relative a l'interface
    gbc.anchor = GridBagConstraints.EAST;							//et commencera par la gauche.

    JLabel
    label = new JLabel ("Upper number", JLabel.TRAILING);   num2m.add (label, gbc);	 //ici on declare les label qui on indiquer quel chiffre y mettre
    label = new JLabel ("Left number", JLabel.TRAILING); num2m.add (label, gbc);
    gbc.gridx++;
    gbc.weightx = 1.0f;     
    num2m.add (tf1, gbc);   										//ici on va intregrer les textField au label declarer precedement
    num2m.add (tf2, gbc);

    JFrame lf = new JFrame ("Arab Multiplication");
    if (JOptionPane.showConfirmDialog (lf, num2m, "Enter 2 real number to multiply",    //cette partie de code est pour afficher un dialogue box
                JOptionPane.OK_CANCEL_OPTION,											// qui va contenir 2 textField ou l'utilisateur doit
                JOptionPane.QUESTION_MESSAGE ) == JOptionPane.OK_OPTION) {				// inserer 2 chiffre entier a multiplier
        try {
            int m = Integer.parseInt (tf1.getText()), n = Integer.parseInt (tf2.getText());		//ici on utilise le try catch pour qu'en cas d'erreur
            Maths lattice = new Maths (m, n, label.getFont());									//le programme ne s'arrete pas mais indique l'erreur
            lf.add (lattice, "Center");															//On va centrer l'interface
            lf.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);									//Cette condition va arreter le programme quand on le ferme
            lf.pack();										
            lf.setVisible (true);																//on va afficher l'interface a l'ecran
        } catch (NumberFormatException nex) {													//Si jamais un chiffre non entier est inserer
            JOptionPane.showMessageDialog (lf, "Invalid numbers to multiply",					//le programme va afficher "Invalid numbers to multiply"
                    "Arab Multiplication Error", JOptionPane.ERROR_MESSAGE);
            System.exit (1);
    }} else {   System.exit (2);
}}}