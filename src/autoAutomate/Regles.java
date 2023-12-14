package src.autoAutomate;

import java.nio.file.*;
import java.io.*;

public class Regles {
    private int dim=0;
    private boolean valide=false;
    private int [][] voisins=null;
    private Condition [] conditions=null;
    private Action [] actions=null;
    private Valeur [] variables=null;
    
    private boolean setCondition (String exp, int num) {
        if (num<0 || conditions.length-1<num) {
            return false;
        }
        exp=(new Immediat ()).deParenthesage(exp);
        conditions[num]=(new OpLogBin ()).getCond(exp,voisins.length,variables);
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
        return actions[num].set(exp,voisins.length,variables);
    }
    
    private boolean setCondActions (String exp) {
        setVariables(exp);
        String [] exps=exp.split(";");
        if (exps.length==0) {
            return false;
        }
        conditions=new Condition [exps.length];
        actions=new Action [exps.length];
        String [] acts=null;
        for (int i=0;i<exps.length;i++) {
            acts=exps[i].split("\\?");
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
            Valeur fonction=new Immediat ();
            for (int i=0;i<dim;i++) {
                if (!fonction.getInt(indices[i],nb)) {
                    return false;
                }
                voisins[j][i]=nb[0];
            }
        }
        return true;
    }

    private boolean addVariable (char v) {
        if (!isVariable(v)) {
            if (variables==null) {
                variables=new Valeur[1];
                variables[0]=new Variable ();
                variables[0].set(v+"",0,0,null);
            }
            else {
                Valeur [] nouv=new Valeur[variables.length+1];
                for (int i=0;i<variables.length;i++) {
                    nouv[i]=variables[i];
                }
                nouv[variables.length]=new Variable ();
                nouv[variables.length].set(v+"",0,0,null);
                variables=nouv;
            }
            return true;
        }
        return false;
    }

    private void setVariables (String exp) {
        Valeur fonction=new Variable ();
        for (int i=0;i<exp.length();i++) {
            if (fonction.getOp(exp.charAt(i)+"")==0) {
                addVariable(exp.charAt(i));
            }
        }
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
        exp=simplification(exp);
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
                exp+=conditions[i].getExp()+" ? "+actions[i].getExp();
            }
            return exp;
        }
        return "Error";
    }
    
    public boolean charger (String fichier) {
        try {
            String exp=new String(Files.readAllBytes(Paths.get(fichier)));
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
                exp+=conditions[i].getExp()+"?\r\n    "+actions[i].getExp()+"\r\n";
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

    public boolean isVariable (char v) {
        if (variables!=null) {
            for (int i=0;i<variables.length;i++) {
                if (((Variable)variables[i]).getNom()==v) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setVar (char nom, double val) {
        if (variables!=null) {
            for (int i=0;i<variables.length;i++) {
                if (nom==((Variable)variables[i]).getNom()) {
                    ((Variable)variables[i]).setVal(val);
                    return true;
                }
            }
        }
        return false;
    }

    public double getVar (char nom) {
        if (variables!=null) {
            for (int i=0;i<variables.length;i++) {
                if (nom==((Variable)variables[i]).getNom()) {
                    return ((Variable)variables[i]).get(null,null,null);
                }
            }
        }
        return 0;
    }

    public char [] getVarList () {
        if (variables!=null) {
            char [] list=new char [variables.length];
            for (int i=0;i<variables.length;i++) {
                list[i]=((Variable)variables[i]).getNom();
            }
            return list;
        }
        return null;
    }

    public int getNbVars () {
        if (variables==null) {
            return 0;
        }
        return variables.length;
    }

    private String simplification (String exp) {
        String [] exps=exp.split("\r?\n|\r");
        String res="";
        for (int i=0;i<exps.length;i++) {
            res+=exps[i];
        }
        exps=res.split(" ");
        res="";
        for (int i=0;i<exps.length;i++) {
            res+=exps[i];
        }
        return res;
    }
    
}