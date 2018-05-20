T1=0.1
T2=0.3
T3=0.6
Tant=0.25
Tdia=0.5
Tseg=0.25

values <- c(12,11,9,8,6,5,8,7,5,4,3,1,12,10,9,7,6,5,3,7,6)

Pseg=(T1*values[1])+(T2*values[8])+(T3*values[15])
Pter=(T1*values[2])+(T2*values[9])+(T3*values[16])
Pqua=(T1*values[3])+(T2*values[10])+(T3*values[17])
Pqui=(T1*values[4])+(T2*values[11])+(T3*values[18])
Psex=(T1*values[5])+(T2*values[12])+(T3*values[19])
Psab=(T1*values[6])+(T2*values[13])+(T3*values[20])
Pdom=(T1*values[7])+(T2*values[14])+(T3*values[21])

PPseg=(Tant*Pdom)+(Tdia*Pseg)+(Tseg*Pter)
PPter=(Tant*Pseg)+(Tdia*Pter)+(Tseg*Pqua)
PPqua=(Tant*Pter)+(Tdia*Pqua)+(Tseg*Pqui)
PPqui=(Tant*Pqua)+(Tdia*Pqui)+(Tseg*Psex)
PPsex=(Tant*Pqui)+(Tdia*Psex)+(Tseg*Psab)
PPsab=(Tant*Psex)+(Tdia*Psab)+(Tseg*Pdom)
PPdom=(Tant*Psab)+(Tdia*Pdom)+(Tseg*Pseg)
