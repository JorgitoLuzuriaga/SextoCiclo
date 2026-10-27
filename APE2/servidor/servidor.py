import os
import time

ENTRADA = "../comunicacion/entrada_servidor.txt"
SALIDA = "../comunicacion/salida_servidor.txt"

ultimo_mensaje = ""


print("===================================")
print("      SERVIDOR INICIADO")
print("===================================")
print("Esperando mensajes...\n")


while True:

    if os.path.exists(ENTRADA):

        with open(ENTRADA, "r", encoding="utf-8") as archivo:
            mensaje = archivo.read().strip()

        if mensaje and mensaje != ultimo_mensaje:

            print("Nuevo mensaje recibido:")
            print(mensaje)

            resultado = mensaje.upper()

            with open(SALIDA, "w", encoding="utf-8") as archivo:
                archivo.write(resultado)

            ultimo_mensaje = mensaje

            print("Mensaje procesado:")
            print(resultado)
            print("-----------------------------------")

    time.sleep(1)