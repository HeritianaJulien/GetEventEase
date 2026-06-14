package com.example.geteventease.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.geteventease.data.FakeData
import com.example.geteventease.data.model.EventCategory
import com.example.geteventease.ui.screens.*
import kotlinx.coroutines.launch

private const val NavAnimDuration = 320

private val screenEnterTransition = fadeIn(tween(NavAnimDuration)) +
    slideInHorizontally(tween(NavAnimDuration)) { fullWidth -> fullWidth / 4 }

private val screenExitTransition = fadeOut(tween(NavAnimDuration)) +
    slideOutHorizontally(tween(NavAnimDuration)) { fullWidth -> -fullWidth / 4 }

private val screenPopEnterTransition = fadeIn(tween(NavAnimDuration)) +
    slideInHorizontally(tween(NavAnimDuration)) { fullWidth -> -fullWidth / 4 }

private val screenPopExitTransition = fadeOut(tween(NavAnimDuration)) +
    slideOutHorizontally(tween(NavAnimDuration)) { fullWidth -> fullWidth / 4 }

object Routes {
    const val SPLASH = "splash"
    const val SELECTION = "selection"
    const val HOME = "home"
    const val EVENTS = "events"
    const val CALENDAR = "calendar"
    const val NOTIFICATIONS = "notifications"
    const val PROFILE = "profile"
    const val EVENT_DETAIL = "event_detail/{eventId}"
    
    const val ADMIN_LOGIN = "admin_login"
    const val ADMIN_DASHBOARD = "admin_dashboard"
    const val ADMIN_ADD_EVENT = "admin_add_event"

    fun eventDetail(eventId: Int) = "event_detail/$eventId"
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem(Routes.HOME, Icons.Default.Home, "Accueil")
    object Events : BottomNavItem(Routes.EVENTS, Icons.Default.ConfirmationNumber, "Événements")
    object Calendar : BottomNavItem(Routes.CALENDAR, Icons.Default.CalendarMonth, "Agenda")
    object Notifications : BottomNavItem(Routes.NOTIFICATIONS, Icons.Default.Notifications, "Alertes")
    object Profile : BottomNavItem(Routes.PROFILE, Icons.Default.Person, "Profil")
}

private fun screenTitle(route: String?): String = when {
    route == Routes.HOME -> "Accueil"
    route == Routes.EVENTS -> "Événements"
    route == Routes.CALENDAR -> "Agenda"
    route == Routes.NOTIFICATIONS -> "Alertes"
    route == Routes.PROFILE -> "Profil"
    route == Routes.SELECTION -> "Bienvenue"
    route == Routes.ADMIN_LOGIN -> "Connexion Admin"
    route == Routes.ADMIN_DASHBOARD -> "Tableau de Bord"
    route == Routes.ADMIN_ADD_EVENT -> "Nouvel Événement"
    route?.startsWith("event_detail/") == true -> "Détails de l'Événement"
    else -> "GET EventEase"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GETEventEaseNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val context = androidx.compose.ui.platform.LocalContext.current
    val repository = remember { 
        com.example.geteventease.data.EventRepository(
            com.example.geteventease.data.local.AppDatabase.getDatabase(context).eventDao()
        )
    }
    
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Events,
        BottomNavItem.Calendar,
        BottomNavItem.Notifications,
        BottomNavItem.Profile
    )
    
    val isBottomBarVisible = bottomNavItems.any { it.route == currentRoute }
    val showBackButton = !isBottomBarVisible && currentRoute != Routes.SPLASH

    Scaffold(
        modifier = modifier,
        topBar = {
            if (currentRoute != Routes.SPLASH) {
                TopAppBar(
                    title = { 
                        Text(
                            text = screenTitle(currentRoute),
                            fontWeight = FontWeight.Bold
                        ) 
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                    ),
                    navigationIcon = {
                        if (showBackButton) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (isBottomBarVisible) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        val scope = androidx.compose.runtime.rememberCoroutineScope()
        NavHost(
            navController = navController,
            startDestination = Routes.SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.SPLASH) {
                SplashScreen(onTimeout = {
                    navController.navigate(Routes.SELECTION) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                })
            }

            composable(Routes.SELECTION) {
                SelectionScreen(
                    onStudentAccess = {
                        navController.navigate(Routes.HOME)
                    },
                    onAdminAccess = {
                        navController.navigate(Routes.ADMIN_LOGIN)
                    }
                )
            }

            composable(Routes.HOME) {
                HomeScreen(
                    onEventClick = { event: com.example.geteventease.data.local.EventEntity ->
                        navController.navigate(Routes.eventDetail(event.id))
                    }
                )
            }

            composable(Routes.EVENTS) {
                EventsScreen(
                    onEventClick = { event ->
                        navController.navigate(Routes.eventDetail(event.id))
                    }
                )
            }

            composable(Routes.CALENDAR) {
                CalendarScreen()
            }

            composable(Routes.NOTIFICATIONS) {
                NotificationsScreen()
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    onAdminClick = {
                        navController.navigate(Routes.ADMIN_LOGIN)
                    }
                )
            }

            composable(
                Routes.EVENT_DETAIL,
                arguments = listOf(navArgument("eventId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("eventId") ?: return@composable
                EventDetailScreen(
                    eventId = id,
                    repository = repository,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.ADMIN_LOGIN) {
                AdminLoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Routes.ADMIN_DASHBOARD) {
                            popUpTo(Routes.ADMIN_LOGIN) { inclusive = true }
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.ADMIN_DASHBOARD) {
                AdminDashboard(
                    repository = repository,
                    onAddEvent = { navController.navigate(Routes.ADMIN_ADD_EVENT) },
                    onEditEvent = { eventId: Int ->
                        // navController.navigate(Routes.adminEditEvent(eventId))
                    },
                    onLogout = {
                        navController.navigate(Routes.SELECTION) {
                            popUpTo(Routes.ADMIN_DASHBOARD) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.ADMIN_ADD_EVENT) {
                AddEventScreen(
                    onEventAdded = { event ->
                        scope.launch {
                            repository.insert(event)
                            navController.popBackStack()
                        }
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
