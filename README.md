# Tema2_IP - Aplicatie Meteo

## Informatii generale

Aplicatia ofera informatii meteo pentru orasele din fisierul _locations.json_. 
Modelul arhitectural folosit este MVC: 
 * Model-ul faci un API_Call catre OpenWeather pentru a prelua datele meteo
 * View-ul este implementat folosind JavaFx si permite selectarea unei tari si a unui oras din tara respectiva si afiseaza informatiile meteo corespunzatoare
 * Controller-ul preia informatiile de la Model si le transmite catre View
 
Aplicatia salveaza cautarile anterioare in fisierul _/src/main/resources/log.txt_
 
## Configurare

Locatiile pentru care sunt oferite date meteo pot fi modificate din fisierul _/src/main/resources/locations.json_.
Exemplu intrare in fisier:

{
     "ID": "2643743",
     "nm": "London",
     "lat": "51.508",
     "lon": "-0.126",
     "countryCode": "GB"
}