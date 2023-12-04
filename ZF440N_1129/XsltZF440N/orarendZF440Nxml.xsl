<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl = "http://www.w3.org/1999/XSL/Transform" version = "1.0" >

	<xsl:template match="/">
		<xsl:text>&#xa;</xsl:text>
		<html>
			<body>
				<h2>Kórád György Kurzusfelvétel – 2023/24. I. félév.</h2>
				
				<table border = "4">
					<tr bgcolor = "#90EE90">
						<th>ID</th>
						<th>Tipus</th>
						<th>Targy</th>
						<th colspan="2">Idopont</th>
						<th>Helyszin</th>
						<th>Oktato</th>
						<th>Szak</th>
					</tr>
					
					<xsl:for-each select="/ZF440N_kurzusfelvetel/kurzusok/kurzus">
						<tr>
							<td><xsl:value-of select = "@id"/></td>
							<td><xsl:value-of select = "@tipus"/></td>
							<td><xsl:value-of select = "targy"/></td>
							<td><xsl:value-of select = "idopont/nap"/></td>
							<td><xsl:value-of select = "idopont/tol"/>h - <xsl:value-of select = "idopont/ig"/>h</td>
							<td><xsl:value-of select = "helyszin"/></td>
							<td><xsl:value-of select = "oktato"/></td>
							<td><xsl:value-of select = "szak"/></td>
						</tr>
					</xsl:for-each>
					
				</table>
			</body>
		</html>
		
	</xsl:template>	
	<xsl:output method="xml" encoding="utf-8" indent="yes"></xsl:output>

</xsl:stylesheet>