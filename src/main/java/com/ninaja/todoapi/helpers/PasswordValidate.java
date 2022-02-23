package com.ninaja.todoapi.helpers;

import java.util.Arrays;
import java.util.List;

public class PasswordValidate {

	    public static Boolean validator (String senha) {
	    	
	        int caracteresAdicionais = 0;

	        boolean possuiNumeros = false;
	        boolean possuiMinusculas = false;
	        boolean possuiMaiusculas = false;
	        boolean possuiCarcteresEspeciais = false;
	        List<Character> especiais = Arrays.asList('!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '+');

	        //Validando se a senha possui...
	        // Contém no mínimo 1 digito.
	        // Contém no mínimo 1 letra em minúsculo.
	        // Contém no mínimo 1 letra em maiúsculo.
	        // Contém no mínimo 1 caractere especial. Os caracteres especiais são: !@#$%^&*()-+
	        for(int i = 0; i < senha.length(); i++){
	            char caractere = senha.charAt(i);
	            int caractereASCII = (int) caractere;

	            if( caractereASCII >= 48 && caractereASCII <=57){
	                //48 a 57: códigos ASCII dos numerais de 0 a 9
	                possuiNumeros = true;
	            } else if( caractereASCII >= 97 && caractereASCII <=122 ){
	                //97 a 122: códigos ASCII das letras minúsculas
	                possuiMinusculas = true;
	            } else if( caractereASCII >= 65 && caractereASCII <=90 ){
	                //65 a 90: códigos ASCII das letras maiúsculas
	                possuiMaiusculas = true;
	            } else {
	                for(Character c: especiais){
	                    if(c == caractere){
	                        possuiCarcteresEspeciais = true;
	                    }
	                }

	            }
	        }

	        //verificando total de caracteres a adicionar por não cumprirem os requisitos
	        if(!possuiNumeros) caracteresAdicionais++;
	        if(!possuiMinusculas) caracteresAdicionais++;
	        if(!possuiMaiusculas) caracteresAdicionais++;
	        if(!possuiCarcteresEspeciais) caracteresAdicionais++;

	        //validando o tamanho da senha - mínimo de 6 caracteres
	        // e imprimindo mensagem final
	        if(senha.length() < 6){
	            return false;
	       
	        } else{
	            if(caracteresAdicionais != 0 ){
	               return false;
	               
	            } else {
	            	return true;
	            }
	        }
	    }
	}

