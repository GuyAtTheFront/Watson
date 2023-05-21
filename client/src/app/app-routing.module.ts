import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { DashboardContainerComponent } from './dashboards/shared/dashboard-container/dashboard-container.component';
import { DashboardOverviewComponent } from './dashboards/dashboard-overview/dashboard-overview.component';
import { DashboardBotManagerComponent } from './dashboards/dashboard-bot-manager/dashboard-bot-manager.component';
import { DashboardPasswordManagerComponent } from './dashboards/dashboard-password-manager/dashboard-password-manager.component';
import { DashboardRssManagerComponent } from './dashboards/dashboard-rss-manager/dashboard-rss-manager.component';
import { DashboardReminderManagerComponent } from './dashboards/dashboard-reminder-manager/dashboard-reminder-manager.component';
import { BotChatComponent } from './bot-chat/bot-chat.component';
import { LandingComponent } from './landing/landing.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  {
    path: "", 
    component: LandingComponent
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "register",
    component: RegisterComponent
  },
  {
    path:"dashboard", 
      children: [
        {
          path: 'overview',
          component: DashboardOverviewComponent
        },
        {
          path: 'manage-bot',
          component: DashboardBotManagerComponent
        },
        {
          path: 'manage-password',
          component: DashboardPasswordManagerComponent
        },
        {
          path: 'manage-feed',
          component: DashboardRssManagerComponent
        },
        {
          path: 'manage-reminder',
          component: DashboardReminderManagerComponent
        },
        {
          path:'',
          redirectTo: '/dashboard/overview',
          pathMatch: 'full'
        },
      ]
  },
  {
    path: "bot/:id", 
    component: BotChatComponent
  }

  // {path:"dashboard/manage-bots", outlet:"dashboard-body", component: DashboardBotManagerComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
