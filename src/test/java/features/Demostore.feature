Feature: buy a item from demostore
  I, as a customer, want to buy an item

  @BUY_ITEM
  Scenario Outline: Buy an item
    Given i access cs-cart website
    When i search for an "<item>"
    And find this product on the grid
    And i add it to the cart
    Then i check the item on the chart and the total price
    #And i do the checkout
    #And i select the "<payment>" Payment
    #Then i proceed with the order
    Examples:
      | item          | payment     |
      | black t-shirt | Phone Order |

