package eg.edu.alexu.csd.datastructure.iceHockey.cs23;

import java.awt.Point;

public class SearchPlayers implements IPlayersFinder {
	
//array to save coordinates of chain
	public int[][] points = new int[2][1000];
	public static int r = 0,m=-1,n=-1,con;
	public static int present=0;
	public static int past=0;
	public int max = 10000;
	
	@Override
	public Point[] findPlayers(String[] photo, int team, int threshold) {
		if(photo.length ==0) {
			return null;
		}else {
		int  placeCOUNTER=0,i,j /**counters**/ , maxrow=0 , maxcol=0 , area=0 ;
		Point[] place = new Point[100];

		
	//convert string array to 2d char arrray
		char[][] charArray = new char[photo.length][];
		for (int x = 0; x < photo.length; x++) {
		    charArray[x] = photo[x].toCharArray();}
		
	//array to check if i've visited the element or not & fill it with zeroes
		int[][] Boo = new int[charArray.length][charArray[0].length];
		for(i=0;i<charArray.length;i++) {
			for(j=0;j<charArray[0].length;j++) {
				Boo[i][j]=0;
			}
		}
		
		for(i=0;i < charArray.length;i++ ) {
			for(j=0;j<charArray[0].length;j++) {
				if(charArray[i][j] == team + '0' && Boo[i][j]==0) {
					Searchfor( i , j, team , charArray, points,  Boo ,-1);
					area = checkarea(Boo);
			//check area 
				if(area<threshold) {
					for(int k=0;k<2;k++) {for(int z=0;z<points[0].length;z++) {points[k][z]=0;}}
					r=0;
				
				}else {
				
			//maximum ,minimum row and column 
				int minrow =points[1][0];
				for(j=0;j<r;j++) {
					if(points[0][j]> maxrow ) {
						maxrow = points[0][j];
					}
					
				    if(points[0][j] < minrow){
					  	  minrow = points[0][j];
					  	}
				}
				int mincol=points[1][0];
				for(j=0;j<r;j++) {
					if(points[1][j]> maxcol ) {
						maxcol = points[1][j];
					}
				    if(points[1][j] < mincol){
				  	  mincol = points[1][j];
				  	}
				}
				
			//store the place of chain 
				place[placeCOUNTER] = new Point();
				place[placeCOUNTER].setLocation(maxcol + mincol + 1, maxrow + minrow + 1);
				place[placeCOUNTER].y = maxrow + minrow + 1;		
				placeCOUNTER ++;
				maxcol=0;mincol=0;maxrow=0;minrow=0;
				
			//make points empty again
				for(int k=0;k<2;k++) {for(int z=0;z<points[0].length;z++) {points[k][z]=0;}}
				r=0;
			}}
			}
		}
		
		return place;}
	}
	public static void up(int i ,int j, int team , char[][] ca,int[][] points,int[][] boo) {
		if(ca[i][j] == team + '0' && boo[i][j]==0 && i != 0) {
			points[0][r]=i;
			points[1][r]=j;
			boo[i][j]=1;
			r++;
			up(--i,j,team,ca,points,boo);
		}
		
	}
	public static void down(int i ,int j, int team , char[][] ca,int[][] points,int[][] boo) {
		if(i != ca.length  /*&& boo[i][j]==0*/ && ca[i][j] == team + '0') {
			points[0][r]=i;
			points[1][r]=j;
			boo[i][j]=1;
			r++;
			down(++i,j,team,ca,points,boo);
		}
		
	}
	public static void left(int i ,int j, int team , char[][] ca,int[][] points,int[][] boo) {
		if(ca[i][j] == team + '0' /*&& boo[i][j]==0*/ && j != 0) {
			points[0][r]=i;
			points[1][r]=j;
			boo[i][j]=1;
			r++;
			left(i,--j,team,ca,points,boo);
		}

		
	}
	public static void right(int i ,int j, int team , char[][] ca,int[][] points,int[][] boo) {
		if(j != ca[0].length /*&& boo[i][j]==0*/ &&  ca[i][j] == team + '0') {
			points[0][r]=i;
			points[1][r]=j;
			boo[i][j]=1;
			r++;
			right(i,++j,team,ca,points,boo);
		}
		
	}
	
	public static void Search(int i ,int j, int team , char[][] charArray,int[][] points,int[][] Boo ) {
		up(i, j, team, charArray, points , Boo);
		down(i, j, team, charArray, points , Boo);
		right(i, j, team, charArray, points , Boo);
		left(i, j, team, charArray, points , Boo);
	}
	
	public static void Searchfor(int i ,int j, int team , char[][] charArray,int[][] points,int[][] Boo ,int t){
		if(t<10) {
			if(charArray[i][j] == team + '0' /*&& Boo[i][j]== 0*/) {
				Search( i , j, team , charArray,points,  Boo );
				r++;
			}
			Searchfor(points[0][++m],points[1][++n], team , charArray , points , Boo,++t);
		}
	}
	public static int checkarea(int[][] Boo) {
		m=-1;n=-1;con=0;
		for(int i =0 ; i<Boo.length;i++) {
			for(int j =0 ;j<Boo[0].length;j++) {
				if(Boo[i][j]==1) {
					present++;
				}
			}
		}
		present = present - past;
		past = present;
		
		return present*4;
		
	}
	
	
	
}
