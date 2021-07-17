<template>
    <div>
        <div v-if="!addReaders && readerInputs.length === 0 && addressBookClosed">
            <button class="light-btn" @click="addReaders = true" style="margin-bottom: 1em">
                <h5>
                    <b-icon icon="plus-circle"></b-icon>
                    {{ $t('Settings.DocumentSettings.addReader') }}
                </h5>
            </button>
        </div>
        <div v-if="!addReaders && !(readerInputs.length === 0) && addressBookClosed">
            <b-list-group-item style="height:2.5em; padding: 0.25em 0.75em" v-for="reader in readers"
                               :key="reader.email"> {{ reader.email }}
            </b-list-group-item>

            <b-row align-h="end">
                <b-col cols="auto">
                    <button class="light-btn" @click="addReaders = true" style="margin:0.5em 0">
                        <h6>
                            <b-icon icon="pencil-fill"></b-icon>
                            {{ $t('Settings.DocumentSettings.edit') }}
                        </h6>
                    </button>
                </b-col>
            </b-row>
        </div>

        <div v-if="addReaders && addressBookClosed">
            <div class="form-group">
                <b-row no-gutters>
                    <b-col cols="12">
                        <b-input-group class="mb-2">
                            <b-input-group-prepend is-text>
                                <b-icon icon="book" style="fill: var(--elsa-blue); cursor: pointer"
                                        @click="addressBook"></b-icon>
                            </b-input-group-prepend>
                            <b-form-tags
                                class="form-control" v-model="readerInput" id="readerInput"
                                separator=","
                                :placeholder="$t('Settings.DocumentSettings.placeholderMail')"
                            ></b-form-tags>
                            <b-input-group-append>
                                <button class="elsa-blue-btn add-button" @click="addReader()"
                                        style="padding: 0.2em 0.4em;">
                                    <b-icon icon="plus" style="width: 1.5em; height: 1.5em"></b-icon>
                                </button>
                            </b-input-group-append>
                        </b-input-group>
                    </b-col>
                </b-row>
            </div>
            <div>
            </div>
            <div class="card" style="height:15em; overflow-y: auto; overflow-x: hidden">
                <div class="drag-drop-element" v-for="reader in readerInputs" :key="reader.email"
                     style="padding:0.25em">
                    <b-row align-h="between">
                        <h6>
                            <b-col cols="auto">
                                <b-icon class="icon-hover" icon="trash" @click="deleteReader(reader)"></b-icon>
                                {{ reader.email }}
                            </b-col>
                        </h6>
                    </b-row>
                </div>
            </div>
            <b-row align-h="end" style="margin-top: 0.5em">
                <b-col cols="auto">
                    <button class="light-btn" @click="cancel()">
                        {{ $t('DownloadDoc.cancel') }}
                    </button>
                </b-col>
                <b-col cols="auto">
                    <button class="elsa-blue-btn" @click="save()">
                        {{ $t('Settings.DocumentSettings.save') }}
                    </button>
                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
export default {
    name: "ReaderMenu",
    props: {
        readers: Array,
        addressBookClosed: Boolean
    },
    data() {
        return {
            readerInput: [],
            readerInputs: [],
            addReaders: false
        }
    },
    mounted() {
        this.readerInputs = this.readers
    },
    methods: {
        addReader() {
            for (var i = 0; i < this.readerInput.length; i++) {
                if (this.readerInputs.includes(this.readerInput[i])) {
                    // TODO: Error
                } else {
                    this.readerInputs.push({email: this.readerInput[i]});
                }
            }
            this.readerInput = [];
        },
        deleteReader(reader) {
            this.readerInputs.splice(this.readerInputs.indexOf(reader), 1)
        },
        cancel() {
            this.addReaders = false;
            this.readerInputs = this.readers;
            this.readerInput = [];
        },
        save() {
            this.$emit('updateReaders', this.readerInputs);
            this.addReaders = false;
        },
        addressBook() {
            this.$emit('showAddressBook')
        }
    }
}
</script>

<style scoped>
.drag-drop-element {
    border-bottom: 0.015em solid var(--light-grey);
}

.icon, .icon-hover {
    fill: var(--dark-grey);
    margin-left: 0.2em;
    margin-right: 0.2em;
}

.icon-hover:hover {
    fill: var(--light-grey);
    transition-duration: 0.4s;
}

.alert {
    background-color: var(--sign-doc-hover);
    color: var(--red);
    border-color: var(--red);
}

.elsa-blue-btn, .light-btn {
    padding: 0.5vh 1vw 0;
    border: 0.03vw solid var(--dark-grey);
    margin: 0.25vh 0.25vw;
}

.elsa-blue-btn:focus, .light-btn:focus {
    border: 0.03vw solid var(--dark-grey);
}


.add-button {
    margin: 0;
    border-color: var(--elsa-blue);
}

.form-control {
    background-color: var(--whitesmoke);
    color: var(--dark-grey);
    border: solid 0.1em var(--elsa-blue);
    border-radius: 0.2em;
}

.input-group > .form-control, .input-group > .form-control-plaintext, .input-group > .custom-select, .input-group > .custom-file {
    height: 1.85em;
}

.form-control:focus, .form-control:active, .b-form-tags.focus, .b-form-tags.active {
    color: var(--dark-grey);
    background-color: var(--whitesmoke);
    border-color: var(--elsa-blue);
    outline: 0;
    box-shadow: 0 0 0 0.2rem var(--elsa-blue-transparent);
}

.form-control::placeholder {
    color: var(--shadow-grey);
    opacity: 1; /* Firefox */
}

.form-control:-ms-input-placeholder {
    color: var(--shadow-grey);
}

.form-control::-ms-input-placeholder {
    color: var(--shadow-grey);
}

/* Settings for differently sized screens */
@media (max-width: 575.98px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.5em;
    }
}

@media (min-width: 576px) and (max-width: 767.98px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.7em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (max-height: 499.98px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.8em;
    }
}

@media (min-width: 768px) and (max-width: 991.98px) and (min-height: 500px) {
    .form-control, .input-group > .input-group-prepend > .input-group-text, .input-group-append {
        font-size: 0.8em;
    }
}
</style>
