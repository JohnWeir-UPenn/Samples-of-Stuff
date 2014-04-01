#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

int main(int argc, char ** argv) {
 
  FILE *ifp = fopen(argv[1], "r");
  FILE *ofp;
  int current;
  char string[200];
  char newFile[200];
  char exit[5] = "EXIT";
  char trueJMP[8] = "trueJMP";
  int ends[1000];
  int worked;
  long pos;
  int check;
  char char_check; 
  int i = 0;
  int comment;
  int hiCur;
  int lowCur;
  int global = 0;
  char globe;
  int ptr = 0;
  int numIF = 0;
  int numEND = 0;
  char it [200];
  int elses[1000];
  int temp = 0;
  
  if (ifp == NULL) {
    printf("No file selected\n");
    return 1;
  }

  for (temp = 0; temp < 100; temp++) {
    elses[temp] = 0;
    ends[temp] = 0;
  }

  while(argv[1][i] != '.') {
    newFile[i] = argv[1][i];
    it[i] = argv[1][i];
    i++;
  }

  newFile[i++] = '.';
  newFile[i++] = 'a';
  newFile[i++] = 's';
  newFile[i++] = 'm';
  newFile[i++] = '\0';

  ofp = fopen(newFile,"w");

  it[i++] = '\0';

  while(!feof(ifp)) {
    int bool = 1;
    
    if (ferror(ifp)) {
      printf("Error reading file\n");
      fclose(ifp);
      fclose(ofp);    
      return 1;
    }
    
    pos = ftell(ifp);
    
    while ((check = getc(ifp)) != EOF) {
      char_check = (char)check;
      if ((char_check == ' ') || (char_check == '\n') || (char_check == '\t')) {
	continue;
      }
      if (char_check == '+') {
	check = getc(ifp);;
	char_check = (char) check;
	if ((char_check == ' ') || (char_check == '\n') || (char_check == '\t') || (char_check == EOF)) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
	  fprintf(ofp,"LDR R1, R6, #0\n");
	  fprintf(ofp,"ADD R0, R1, R0\n");
	  fprintf(ofp,"STR R0, R6, #0\n");  
	  bool=0;
	  break;
	}
	else break;
      }
      if (char_check == '-') {
	check = getc(ifp);;
	char_check = (char) check;
	if ((char_check == ' ') || (char_check == '\n') || (char_check == '\t') || (char_check == EOF)) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"SUB R0, R0, R1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  bool=0;
	  break;
	}
	else break;
      }
      else break;
    }
    
    if (bool == 0) continue;
    else fseek(ifp, pos, SEEK_SET);
    
    if((worked = fscanf(ifp, "%i", &current)) > 0) {
      hiCur = current >> 8;
      hiCur = hiCur & 0xFF;
      lowCur = current & 0xFF;
      fprintf(ofp,"CONST R0, #%d\n", lowCur);
      fprintf(ofp,"HICONST R0, #%d\n", hiCur);
      fprintf(ofp,"ADD R6, R6, #-1\n");
      fprintf(ofp,"STR R0, R6, #0\n");
    }
    
    if (worked < 1) {
      global++;
      if((worked = fscanf(ifp, "%s", string)) > 0){
	if (strncmp(string, "*", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"MUL R0, R0, R1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	}
	else if (strncmp(string, "%", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"MOD R0, R0, R1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	}
	else if (strncmp(string, "/", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"SUB R0, R0, R1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	}
	else if (strncmp(string, "lt", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"CMP R0, R1\n");
	  fprintf(ofp,"BRn %s%s%d\n", trueJMP, it, global);
	  fprintf(ofp,"CONST R0, #0\n"); 
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"BRnzp %s%s%d\n", exit, it, global);
	  fprintf(ofp,"%s%s%d\n", trueJMP, it, global);
	  fprintf(ofp,"CONST R0, #1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"%s%s%d\n", exit, it, global);
	}
	else if (strncmp(string, "le", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"CMP R0, R1\n");
	  fprintf(ofp,"BRnz %s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #0\n"); 
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"BRnzp %s%s%d\n", exit,it, global);
	  fprintf(ofp,"%s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"%s%s%d\n", exit,it, global);
	}
	else if (strncmp(string, "eq", 200) == 0) { 
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"CMP R0, R1\n");
	  fprintf(ofp,"BRz %s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #0\n"); 
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"BRnzp %s%s%d\n", exit,it, global);
	  fprintf(ofp,"%s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"%s%s%d\n", exit,it, global);
	}
	else if (strncmp(string, "ge", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"CMP R0, R1\n");
	  fprintf(ofp,"BRzp %s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #0\n"); 
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"BRnzp %s%s%d\n", exit,it, global);
	  fprintf(ofp,"%s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"%s%s%d\n", exit,it, global);
	}
	else if (strncmp(string, "gt", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"CMP R0, R1\n");
	  fprintf(ofp,"BRn %s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #0\n"); 
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"BRp %s%s%d\n", exit,it, global);
	  fprintf(ofp,"%s%s%d\n", trueJMP,it, global);
	  fprintf(ofp,"CONST R0, #1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"%s%s%d\n", exit,it, global);
	}
	else if (strncmp(string, "and", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"AND R0, R0, R1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	}
	else if (strncmp(string, "not", 200) == 0) { 	 
	  fprintf(ofp,"LDR R0, R6, #0\n");
          fprintf(ofp,"NOT R0, R0\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	}
	else if (strncmp(string, "or", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
          fprintf(ofp,"OR R0, R0, R1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	}
	else if (strncmp(string, "drop", 200) == 0) {
	  fprintf(ofp, "ADD R6, R6, #1\n");
	}
	else if (strncmp(string, "dup", 200) == 0) {
	  fprintf(ofp, "LDR R0, R6, #0\n");
	  fprintf(ofp, "ADD R6, R6, #-1\n");
	  fprintf(ofp, "STR R0, R6, #0\n");
	}
	else if (strncmp(string, "swap", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #-1\n");
	  fprintf(ofp,"STR R1, R6, #0\n");
	}
	else if (strncmp(string, "rot", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"LDR R1, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
	  fprintf(ofp,"LDR R2, R6, #0\n");
	  fprintf(ofp,"STR R1, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #-1\n");
	  fprintf(ofp,"STR R0, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #-1\n");
	  fprintf(ofp,"STR R2, R6, #0\n");
	}
	else if (strncmp(string, "pick", 200) == 0) {
	  fprintf(ofp,"LDR R0, R6, #0\n");
	  fprintf(ofp,"ADD R1, R6, #0\n");
	  fprintf(ofp,"COMPARE%s%d\n",it, global);
	  fprintf(ofp,"CMPI R0, #0\n");
	  fprintf(ofp,"BRn NEXT%s%d\n",it, global);
	  fprintf(ofp,"ADD R6, R6, #1\n");
	  fprintf(ofp,"LDR R2, R6, #0\n");
	  fprintf(ofp,"ADD R0, R0, #-1\n");
	  fprintf(ofp,"BRnzp COMPARE%s%d\n",it, global);
	  fprintf(ofp,"NEXT%s%d\n",it, global);
	  fprintf(ofp,"ADD R6, R1, #0\n");
	  fprintf(ofp,"STR R2, R6, #0\n");
	}
	else if (strncmp(string, "if", 200) == 0) {
	  numIF++;
	  fprintf(ofp,"LDR R4, R6, #0\n");
	  fprintf(ofp,"ADD R6, R6, #1\n");
          fprintf(ofp,"CMPI R4, #0\n");
	  fprintf(ofp,"BRz EXIT%s%d\n", it, numIF);
	}
	else if (strncmp(string, "else", 200) == 0) {
	  for (temp = numIF; temp >= 0; temp --){
	    if (elses[temp] == 0){ 
	      elses[temp] = 1;
	      break;
	    }
	  }
	  fprintf(ofp,"BRnzp DEAD%s%d\n", it, temp); 	  
	  fprintf(ofp,"EXIT%s%d\n",it,temp);
	}
	else if (strncmp(string, "endif", 200) == 0) {
	  for (temp = numIF; temp>=0; temp--) {
	    if (ends[temp]== 0) {
	      ends[temp] = 1;
	      break;
	    }
	  }
	  if (elses[temp] == 0) {
	    fprintf(ofp, "EXIT%s%d\n", it, temp);
	    elses[temp]= 1;	
	  }
	  fprintf(ofp,"DEAD%s%d\n", it, temp);
	}
	else if (strncmp(string, ";", 200) == 0) {
	  while ((comment = fgetc(ifp)) != EOF) {
	    if ((char)comment == '\n') {
	      break;
	    }
	  }
	}
	else if (strncmp(string, ";;", 200) == 0) {
	  while ((comment = fgetc(ifp)) != EOF) {
	    if ((char)comment == '\n') {
	      break;
	    }
	  }
	}
	else if (strncmp(string, "defun", 200) == 0) {
	  if((worked = fscanf(ifp, "%s", string)) > 0) {
	    fprintf(ofp,".CODE\n");	    
	    fprintf(ofp,".FALIGN\n");
	    fprintf(ofp,"%s\n",string);
	    fprintf(ofp,"ADD R6, R6, #-3\n");
	    fprintf(ofp,"STR R7, R6, #1\n"); // save RA
	    fprintf(ofp,"STR R5, R6, #0\n"); // save FP
	    fprintf(ofp,"ADD R5, R6, #0\n");
	  }
	}
	else if (strncmp(string, "return", 200) == 0) {
	  fprintf(ofp,"LDR R7, R6, #0\n");	  
	  fprintf(ofp,"ADD R6, R5, #0\n");
	  fprintf(ofp,"STR R7, R5, #2\n");
	  fprintf(ofp,"LDR R5, R5, #0\n");
	  fprintf(ofp,"LDR R7, R6, #1\n");
	  fprintf(ofp,"ADD R6, R6, #2\n");
	  fprintf(ofp,"RET\n");
	}
	else {
	  fprintf(ofp, "JSR %s\n",string);
	}
      }
    }
   
    if (worked == EOF) {
      break;
    }
    
    if (worked < 1) {
      printf("ERROR: invalid input type");
      fclose(ifp);   
      fclose(ofp);  
      return 1;
   }
  }
  fclose(ofp);
  fclose(ifp);
  return 0;
}
