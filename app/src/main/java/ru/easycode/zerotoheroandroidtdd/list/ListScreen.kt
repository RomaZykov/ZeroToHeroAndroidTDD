package ru.easycode.zerotoheroandroidtdd.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.easycode.zerotoheroandroidtdd.Screen
import ru.easycode.zerotoheroandroidtdd.create.CreateFragment
import ru.easycode.zerotoheroandroidtdd.create.CreateScreen

object ListScreen : Screen.Replace(ListFragment())