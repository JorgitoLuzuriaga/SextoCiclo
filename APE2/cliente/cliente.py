import time

ENTRADA = "../comunicacion/entrada_servidor.txt"
SALIDA = "../comunicacion/salida_servidor.txt"


print("===================================")
print("        CLIENTE INICIADO")
print("===================================")


mensaje = input("Ingrese un mensaje: ")

# Escribir mensaje para el servidor
with open(ENTRADA, "w", encoding="utf-8") as archivo:
    archivo.write(mensaje)

print("\nMensaje enviado al servidor.")
print("Esperando respuesta...")

# Simular latencia
time.sleep(2)

# Leer respuesta
with open(SALIDA, "r", encoding="utf-8") as archivo:
    respuesta = archivo.read().strip()

print("\nRespuesta del servidor:")
print(respuesta)