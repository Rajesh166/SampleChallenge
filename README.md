# SampleChallenge

Sample coding challenge to display list of Restaurants and Restaurant Details Screen.

Integrated various Jet Pack Components like ViewModel, Data Binding.

Used Kotlin co-routine framework.

Followed MVVM Architecture.




Issues to be addressed:
When using also along with lazy intialization of variables had few JDK issues like (same as https://github.com/Kotlin/kotlinx.coroutines/issues/1300) running unit tests. To avoid this issue removed the lazy initialization of variables from viewmodels.

TODO:
1) Add offset and limit to get list of items instead of loading complete list
2) Add better test coverage for co-routine components.
