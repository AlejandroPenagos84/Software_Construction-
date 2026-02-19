from controlador.controlador import Controlador
from operation.Exponencial import Exponencial
from vista.vista import VistaMenu


c = Controlador(VistaMenu(),Exponencial())
c.start()