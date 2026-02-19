from abc import ABC, abstractmethod
class Operation(ABC):
    @abstractmethod
    def calcular(self, num, termino):
        pass
