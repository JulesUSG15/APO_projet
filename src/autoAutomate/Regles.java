package src.autoAutomate;

import java.nio.file.*;
import java.io.*;

public class Regles {
    private int dim=0;
    private boolean valide=false;
    private int [][] voisins=null;
    private Condition [] conditions=null;
    private Action [] actions=null;
    
    private boolean setCondition (String exp, int num) {
        if (num<0 || conditions.length-1<num) {
            return false;
        }
        exp=(new Immediat ()).simplification(exp);
        conditions[num]=(new OpLogBin ()).getCond(exp,voisins.length);
        if (conditions[num]==null) {
            return false;
        }
        return true;
    }
    
    private boolean setAction (String exp, int num) {
        if (num<0 || conditions.length-1<num) {
            return false;
        }
        actions[num]=new Action ();
        return actions[num].set(exp,voisins.length);
    }
    
    private boolean setCondActions (String exp) {
        while (exp.length()>0 && exp.charAt(exp.length()-1)==' ') {
            exp=exp.substring(0,exp.length()-1);
        }
        String [] exps=exp.split(";");
        if (exps.length==0) {
            return false;
        }
        conditions=new Condition [exps.length];
        actions=new Action [exps.length];
        String [] acts=null;
        for (int i=0;i<exps.length;i++) {
            acts=exps[i].split("/");
            if (acts==null || acts.length!=2) {
                return false;
            }
            if (!setCondition(acts[0],i)) {
                return false;
            }
            if (!setAction(acts[1],i)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean setVoisins (String exp) {
        while (exp.length()>0 && exp.charAt(exp.length()-1)==' ') {
            exp=exp.substring(0,exp.length()-1);
        }
        String [] vois=exp.split(";");
        if (vois==null || vois.length<1) {
            return false;
        }
        String [] indices=vois[0].split(",");
        if (indices==null || indices.length<1) {
            return false;
        }
        dim=indices.length;
        voisins=new int [vois.length][dim];
        for (int j=0;j<vois.length;j++) {
            indices=vois[j].split(",");
            if (indices==null || indices.length!=dim) {
                return false;
            }
            int [] nb=new int [1];
            for (int i=0;i<dim;i++) {
                if (!(new Immediat ()).getInt(indices[i],nb)) {
                    return false;
                }
                voisins[j][i]=nb[0];
            }
        }
        return true;
    }
    
    private double get (Tableau tab, int [] indices) {
        int [][] vois=new int [voisins.length][dim];
        for (int j=0;j<vois.length;j++) {
            for (int i=0;i<dim;i++) {
                vois[j][i]=indices[i]+voisins[j][i];
            }
        }
        for (int i=0;i<conditions.length;i++) {
            if (conditions[i].get(tab, vois, indices)) {
                return actions[i].get(tab,vois,indices);
            }
        }
        return tab.getVal(indices);
    }

    public boolean set (String exp) {
        String [] exps=exp.split("@");
        valide=!(exps==null || exps.length!=2);
        if (!valide) {
            return false;
        }
        valide=setVoisins(exps[0]);
        if (!valide) {
            return false;
        }
        valide=setCondActions(exps[1]);
        return valide;
    }
    
    public Tableau appliquer (Tableau tab) {
        if (tab.getDim()!=dim || !valide) {
            return tab;
        }
        int [] indices= new int [dim];
        int taille=tab.getTaille();
        Tableau res=new Tableau (dim,taille);
        while (indices[0]<taille) {
            for (int i=0;i<dim;i++) {
                if (indices[i]>=taille) {
                    indices[i]=0;
                }
            }
            res.setVal(indices,get(tab,indices));
            indices[dim-1]++;
            for (int i=dim-2;i>=0;i--) {
                if (indices[i+1]>=taille) {
                    indices[i]++;
                }
            }
        }
        return res;
    }
    
    public String getExp () {
        if (valide) {
            String exp="";
            for (int j=0;j<voisins.length;j++) {
                exp+=voisins[j][0];
                for (int i=1;i<dim;i++) {
                    exp+=","+voisins[j][i];
                }
                exp+="; ";
            }
            exp+="@ ";
            for (int i=0;i<conditions.length;i++) {
                exp+=conditions[i].getExp()+" / "+actions[i].getExp();
            }
            return exp;
        }
        return "Error";
    }
    
    public boolean charger (String fichier) {
        try {
            String exp=new String(Files.readAllBytes(Paths.get(fichier)));
            String [] exps=exp.split("\r?\n|\r");
            exp="";
            for (int i=0;i<exps.length;i++) {
                exp+=exps[i];
            }
            return set(exp);
        }
        catch (IOException e) {
            return false;
        }
    }
    
    public boolean sauvegarder (String fichier) {
        if (valide) {
            String exp="";
            for (int j=0;j<voisins.length;j++) {
                exp+=voisins[j][0];
                for (int i=1;i<dim;i++) {
                    exp+=","+voisins[j][i];
                }
                exp+=";\r\n";
            }
            exp+="@\r\n\r";
            for (int i=0;i<conditions.length;i++) {
                exp+=conditions[i].getExp()+"/\r\n    "+actions[i].getExp()+"\r\n";
            }
            try {
                Files.write(Paths.get(fichier), exp.getBytes());
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
    
}