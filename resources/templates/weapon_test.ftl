${weapon.name}
${weapon.critRange}
Damage:
<#list weapon.damage as damage>
  ${damage.damage} ${damage.type}
</#list>
AttackMods:
<#list weapon.attacks as attack>
   ${attack.modifier}
</#list>