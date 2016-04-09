# salestaxes
Il progetto maven contiene tre moduli:
* domain: che contiene il modello dati
* data: che contiene le componenti JPA per l'accesso alla base dati
* services: contiene i service che implementano le logiche di business

E' stati scelto di configurare a livello di DB le tasse da applicare ai vari beni in modo da poterle variare senza interventi sul codice applicativo

La classe di test `IntegrationTest` contiene il test di integrazione che esegue l'esercizio richiesto.

La classe di test `ShoppingReceiptServiceImplTest` è stata usata per le implementazioni del Service contenente le lgiche di Business con approccio TDD.

La classe `DataTest` contiene la suite di test per la parte JPA.

Per compilare il progetto `mvn clean install`

La suite completa di test può essere eseguita con `mvn test`, nella console l'output dell'esercizio.

### Esempio di output
`00:33:19.550 [main] INFO  c.l.t.s.ShoppingReceiptServiceImpl - start processing shoppingBasket`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - input ShoppingBasketImpl [goods=[GoodImpl [name=bottle of perfume imp, type=OTHER, imported=true, taxRate=null, grossCost=27.99, quantity=1], GoodImpl [name=bottle of perfume, type=OTHER, imported=false, taxRate=null, grossCost=18.99, quantity=1], GoodImpl [name=packet of headache pills, type=MEDICAL, imported=false, taxRate=null, grossCost=9.75, quantity=1], GoodImpl [name=chocolates, type=FOOD, imported=true, taxRate=null, grossCost=11.25, quantity=1]], total=null, salesTaxes=null]`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - start filling taxes`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - tax for GoodImpl [name=bottle of perfume imp, type=OTHER, imported=true, taxRate=null, grossCost=27.99, quantity=1] is Tax [id=4, goodType=OTHER, tax=0.1, importedTax=0.05]`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - tax for GoodImpl [name=bottle of perfume, type=OTHER, imported=false, taxRate=null, grossCost=18.99, quantity=1] is Tax [id=4, goodType=OTHER, tax=0.1, importedTax=0.05]`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - tax for GoodImpl [name=packet of headache pills, type=MEDICAL, imported=false, taxRate=null, grossCost=9.75, quantity=1] is Tax [id=2, goodType=MEDICAL, tax=0.0, importedTax=0.05]`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - tax for GoodImpl [name=chocolates, type=FOOD, imported=true, taxRate=null, grossCost=11.25, quantity=1] is Tax [id=3, goodType=FOOD, tax=0.0, importedTax=0.05]`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - end filling taxes`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - grandTotal is 74,68`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - taxes are 6,7`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - ------------------------------`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - 1 imported bottle of perfume imp : 32,19`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - 1 bottle of perfume : 20,89`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - 1 packet of headache pills : 9,75`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - 1 imported chocolates : 11,85`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - Sales Taxes: 6,7`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - Total: 74,68`

`00:33:19.550 [main] DEBUG c.l.t.s.ShoppingReceiptServiceImpl - ------------------------------`

`00:33:19.550 [main] INFO  c.l.t.s.ShoppingReceiptServiceImpl - end processing shoppingBasket`
