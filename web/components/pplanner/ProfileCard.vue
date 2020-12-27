<template>
  <v-row justify="center">
    <v-col cols="12" md="4">
      <v-card flat>
        <v-layout justify-center>
          <v-img :src="member.avatar" contain max-width="25em">
            <v-footer v-if="writable" absolute color="blue-grey">
              <v-layout justify-center>
                <v-btn dark text>Modifier la photo</v-btn>
              </v-layout>
            </v-footer>
          </v-img>
        </v-layout>
      </v-card>
    </v-col>
    <v-col cols="12" md="8">
      <v-tabs>
        <v-tab>A propos...</v-tab>
        <v-tab>Timeline</v-tab>

        <v-tab-item>
          <!--About-->

          <v-card flat>
            <v-card-title>
              <div class="subtitle-1 grey--text">Informations basiques</div>
            </v-card-title>
            <v-card-text>
              <table>
                <tr>
                  <th style="width: 10em"></th>
                  <th></th>
                </tr>
                <tbody>
                <tr>
                  <td class="font-weight-bold">Job</td>
                  <td>{{ member.job }}</td>
                </tr>
                <tr>
                  <td class="font-weight-bold">Nom</td>
                  <td>{{ member.fullname }}</td>
                </tr>
                <tr>
                  <td class="font-weight-bold">Email</td>
                  <td>{{ member.email }}</td>
                </tr>
                <tr>
                  <td class="font-weight-bold">Anniversaire</td>
                  <td>{{ member.birthday }}</td>
                </tr>
                </tbody>
              </table>
            </v-card-text>
          </v-card>
        </v-tab-item>

        <v-tab-item>
          <!--Timeline-->

          <v-container fluid>
            <v-timeline clipped dense>

              <v-timeline-item class="mb-6" hide-dot>
                <span>TODAY</span>
              </v-timeline-item>

              <v-timeline-item class="mb-4" color="grey" icon-color="grey lighten-2" small>
                <v-row justify="space-between">
                  <v-col cols="7">
                    This order was archived.
                  </v-col>
                  <v-col class="text-right" cols="5">
                    15:26 EDT
                  </v-col>
                </v-row>
              </v-timeline-item>

              <v-timeline-item class="mb-4" small>
                <v-row justify="space-between">
                  <v-col cols="7">
                    <v-chip class="white--text ml-0" color="purple" label small>
                      APP
                    </v-chip>
                    Digital Downloads fulfilled 1 item.
                  </v-col>
                  <v-col class="text-right" cols="5">
                    15:25 EDT
                  </v-col>
                </v-row>
              </v-timeline-item>

              <v-timeline-item class="mb-4" color="grey" small>
                <v-row justify="space-between">
                  <v-col cols="7">
                    Order confirmation email was sent to John Leider (john@vuetifyjs.com).
                  </v-col>
                  <v-col class="text-right" cols="5">
                    15:25 EDT
                  </v-col>
                </v-row>
              </v-timeline-item>

              <v-timeline-item class="mb-4" hide-dot>
                <v-btn class="mx-0">
                  Resend Email
                </v-btn>
              </v-timeline-item>

              <v-timeline-item class="mb-4" color="grey" small>
                <v-row justify="space-between">
                  <v-col cols="7">
                    A $15.00 USD payment was processed on PayPal Express Checkout
                  </v-col>
                  <v-col class="text-right" cols="5">
                    15:25 EDT
                  </v-col>
                </v-row>
              </v-timeline-item>

              <v-timeline-item color="grey" small>
                <v-row justify="space-between">
                  <v-col cols="7">
                    John Leider placed this order on Online Store (checkout #1937432132572).
                  </v-col>
                  <v-col class="text-right" cols="5">
                    15:25 EDT
                  </v-col>
                </v-row>
              </v-timeline-item>
            </v-timeline>
          </v-container>

        </v-tab-item>

      </v-tabs>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'nuxt-property-decorator'

@Component({})
export default class ProfileCard extends Vue {

  @Prop(Object) readonly member?: Member
  @Prop({ type: Boolean, default: false }) readonly writable?: boolean

}
</script>

<style scoped>

</style>
