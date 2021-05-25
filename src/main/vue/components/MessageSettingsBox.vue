<template>
    <b-container fluid="sm">

        <b-row align-h="center" align-v="center" class="text-center">
            <div class="card">
                <div class="card-header" style="background-color: var(--elsa-blue); color: var(--whitesmoke);">
                    <h4>
                        {{ $t('MessageSettings.settings') }}
                    </h4>
                </div>
                <b-list-group flush>
                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                          <span>
                             {{ $t('MessageSettings.toDo.text') }}
                               <b-icon id="tooltip-target-1" icon="info-circle-fill" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-1" triggers="hover">
                                   {{ $t('MessageSettings.toDo.exp') }}
                              </b-tooltip>
                          </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge @click="clicked = !clicked" :class="[clicked ? 'badge-on' : 'badge-off']">
                                {{ clicked }}
                            </b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                              {{ $t('MessageSettings.sign.text') }}
                             <b-icon id="tooltip-target-2" icon="info-circle-fill" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-2" triggers="hover">
                                   {{ $t('MessageSettings.sign.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                                   {{ $t('MessageSettings.read.text') }}
                             <b-icon id="tooltip-target-3" icon="info-circle-fill" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-3" triggers="hover">
                                   {{ $t('MessageSettings.read.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('MessageSettings.progress.text') }}
                             <b-icon id="tooltip-target-4" icon="info-circle-fill" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-4" triggers="hover">
                                   {{ $t('MessageSettings.progress.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                            {{ $t('MessageSettings.newVersion.text') }}
                             <b-icon id="tooltip-target-5" icon="info-circle-fill" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-5" triggers="hover">
                                   {{ $t('MessageSettings.newVersion.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-on">On</b-badge>
                        </h4>
                    </b-list-group-item>

                    <b-list-group-item class="d-flex justify-content-between align-items-center">
                        <span>
                             {{ $t('MessageSettings.protocol.text') }}
                             <b-icon id="tooltip-target-6" icon="info-circle-fill" class="my-icon"></b-icon>
                              <b-tooltip target="tooltip-target-6" triggers="hover">
                                   {{ $t('MessageSettings.protocol.exp') }}
                              </b-tooltip>
                        </span>
                        <!-- TODO: Add badge-on/badge-off class depending on user preference, same for text-->
                        <h4>
                            <b-badge class="badge-off">Off</b-badge>
                        </h4>
                    </b-list-group-item>
                </b-list-group>
            </div>
        </b-row>

    </b-container>
</template>

<script>
export default {
    name: "MessageSettingsBox",
    data() {
        return {
            clicked: false
        }
    }
}
</script>

<style scoped>

.my-icon {
    fill: var(--elsa-blue);
    height: 1em;
    width: auto;
}

/* Badges aka On/off switches */
.badge-on {
    background-color: var(--elsa-blue);
    color: var(--whitesmoke);
}

.badge-off {
    background-color: var(--elsa-blue-transparent);
    color: var(--shadow-grey);
}

.badge-on:hover, .badge-off:hover {
    background-color: var(--open-doc-hover);
    transition: 0.4s;
}

.badge-off:hover {
    color: var(--whitesmoke);
}

.list-group-item {
    background-color: var(--user-info)
}

.card {
    width: 100%;
    margin-bottom: 5vh;
    background-color: var(--whitesmoke);
    border-color: var(--dark-grey);
}

/* Extra small devices (portrait phones, less than 576px) */
@media (max-width: 575.98px) {
    h4 {
        font-size: .92em;
        text-overflow-mode: ellipse;
    }

    span {
        font-size: 0.6em;
    }

}

/* Small devices (landscape phones, 576px and up) */
@media (min-width: 576px) and (max-width: 767.98px) {
    h4 {
        font-size: .92em;
        text-overflow-mode: ellipse;
    }

    span {
        font-size: 0.7em;
    }

}

/* Large Phones in landscape mode */
@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    h4 {
        font-size: .92em;
        text-overflow-mode: ellipse;
    }

    span {
        font-size: 0.7em;
    }
}

/* Medium devices (tablets, 768px and up) */
@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    h4 {
        font-size: 1.25em;
        text-overflow-mode: ellipse;
    }

    span {
        font-size: 0.7em;
    }

}

/* Large devices (desktops, 992px and up)*/
@media (min-width: 992px) and (max-width: 1199.98px) {
    h4 {
        font-size: 1.2em;
        text-overflow-mode: ellipse;
    }

    span {
        font-size: 0.9em;
    }
}

/* Extra large devices (large desktops, 1200px and up)*/
@media (min-width: 1200px) {
    h4 {
        font-size: 1.3em;
        text-overflow-mode: ellipse;
    }
}


</style>
