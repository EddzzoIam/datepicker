Feature:

Scenario Outline: Select date of travel

Given I navigate to skyscanner web site
When I enter From as "<depatureLocation>"
And I enter To as "<arrivalLocation>"
And I enter depature date>
And I enter return arrival date>
And I enter adults as "<numbAdult>"
And I enter children as "<numbChildren>"
And I select cabin as "<cabinClass>"
And I click search
Then A search page is displayed

Examples:

|depatureLocation |arrivalLocation |cabinClass |numbAdult|numbChildren  |
|LHR              |SJD             |Premium    |2        |2             |
