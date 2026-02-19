class Controlador():
    def __init__(self,vistaMenu,operacion):
        self._vistaMenu = vistaMenu
        self._operacion = operacion
    
    def start(self):
        n,t = self._vistaMenu.obtener_datos()
        r = self._operacion.calcular(n,t)
        self._vistaMenu.imprimir("El resultado de la operacion es: " + str(r))