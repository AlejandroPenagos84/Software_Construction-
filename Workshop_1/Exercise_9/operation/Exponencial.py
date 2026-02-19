from operation.Operation import Operation
import numpy as np

class Exponencial(Operation):
   def calcular(self, numero, terminos):
        """
        Calcula e^numero usando serie de Taylor:
        e^x = 1 + x + x^2/2! + x^3/3! + ... + x^n/n!
        
        Método estable: cada término se calcula como:
        termino[i] = termino[i-1] * x / i
        """
        n = np.arange(1, terminos, dtype=np.float64)
        numero = np.float64(numero)
        
        # Calcular ratios: x/1, x/2, x/3, ..., x/(terminos-1)
        ratios = numero / n
        
        # Producto acumulativo da: x/1, x²/2!, x³/3!, ..., x^n/n!
        terminos_serie = np.cumprod(ratios)
        
        # Sumar 1 (primer término x^0/0!) + resto de términos
        resultado = 1.0 + np.sum(terminos_serie)
        
        return resultado
