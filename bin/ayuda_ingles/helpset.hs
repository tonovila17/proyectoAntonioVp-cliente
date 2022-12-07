<?xml version="1.0" encoding='ISO-8859-1' ?>
<helpset version="1.0">
   <title>Ayuda Aplicación Peliculas</title>
   <maps>
      <!-- Pagina por defecto al mostrar la ayuda -->
      <homeID>inicio</homeID>
      <!-- Que mapa deseamos -->
      <mapref location="map_file.jhm"/>
   </maps>
  

   <!-- Las Vistas que deseamos mostrar en la ayuda -->
   <!-- La tabla de contenidos -->
   <view>
      <name>Tabla Contenidos</name>
      <label>Tabla de contenidos</label>
      <type>javax.help.TOCView</type>
      <data>toc.xml</data>
   </view>

   <!-- El indice -->
   <view>
      <name>Indice</name>
      <label>El indice</label>
      <type>javax.help.IndexView</type>
      <data>indice.xml</data>
   </view>
   
    <view>
      <name>Favoritos</name>
      <label>Favoritos</label>
      <type>javax.help.FavoritesView</type>
   </view>

  

</helpset>