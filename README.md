# PLEASE NOTE!
The application comes in two build flavours: `production` and `mock`:
- please use the `production` flavour to run the application;
- please use the `mock` flavour to run the Integration Tests;
- often the Marvel api takes a lot to respond.

### Updates 2018
- Use Kotlin
- Query only last month comics (we don't get images otherwise)
- Remove data binding
- Remove Icepick (does not support Kotlin)
- ~~Use retained fragment to keep model across configuration changes~~
- Add a simple activity transition
- Use Architecture Components ViewModel

### Tests
Junit and UI tests are in the respective directories.
The UI tests are executed using Espresso and Mockito.

### Features
- Dagger 2
- MVP pattern
- ~~MVVM pattern (Data Binding) (package com.marvel.mauriziopietrantuono.marvel.viewmodel and com.marvel.mauriziopietrantuono.marvel.detail)~~
- ~~Butterknife~~ Kotlin Extensions
- Retrofit + Gson
- RxAndroid + ~~Retrolamda~~
- Maximizing the number of comics and pages for a given budget is a multiple knapsack problem, which is NP complete.
Therefore I use a greedy approximation: I calculate the price per page of each comic,I  sort the comics based on that ratio in ascending order and add comics to the solution until de budget is consumed.
- ~~uses Icepick for simplicity~~

### TODO
- Api keys should not be stored in the source code (maybe use environment variables instead)
- REMOVE OLD VIEWMODEL (NOT NEEDED BECAUSE WE ARE NOT USING DATATBINDING ANYMORE)









