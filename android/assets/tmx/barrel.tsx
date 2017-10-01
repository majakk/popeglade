<?xml version="1.0" encoding="UTF-8"?>
<tileset name="barrel" tilewidth="32" tileheight="32" tilecount="8" columns="4">
 <image source="LPC Base Assets/tiles/barrel.png" width="128" height="64"/>
 <tile id="0">
  <properties>
   <property name="wall" value="true"/>
  </properties>
  <objectgroup draworder="index">
   <properties>
    <property name="wall" type="bool" value="true"/>
   </properties>
  </objectgroup>
 </tile>
 <tile id="4">
  <properties>
   <property name="wall" type="bool" value="true"/>
  </properties>
  <objectgroup draworder="index">
   <properties>
    <property name="wall" type="bool" value="true"/>
   </properties>
   <object id="1" x="4" y="2" width="23" height="12"/>
  </objectgroup>
 </tile>
 <tile id="5">
  <objectgroup draworder="index">
   <properties>
    <property name="wall" type="bool" value="true"/>
   </properties>
  </objectgroup>
 </tile>
</tileset>
