/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package org.apache.directory.studio.apacheds.configuration.editor.v152;


import org.apache.directory.studio.apacheds.configuration.ApacheDSConfigurationPluginConstants;
import org.apache.directory.studio.apacheds.configuration.editor.ServerConfigurationEditor;
import org.apache.directory.studio.apacheds.configuration.model.v152.ServerConfigurationV152;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;


/**
 * This class represents the General Page of the Server Configuration Editor.
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
public class GeneralPage extends FormPage
{
    /** The Page ID*/
    public static final String ID = ServerConfigurationEditor.ID + ".V152.GeneralPage"; //$NON-NLS-1$

    /** The Page Title */
    private static final String TITLE = "General";

    // UI Fields
    private Button allowAnonymousAccessCheckbox;
    private Text maxTimeLimitText;
    private Text maxSizeLimitText;
    private Text synchPeriodText;
    private Text maxThreadsText;
    private Button enableAccesControlCheckbox;
    private Button enableKerberosCheckbox;
    private Button enableChangePasswordCheckbox;
    private Button denormalizeOpAttrCheckbox;
    private Button enableLdapCheckbox;
    private Text ldapPortText;
    private Button enableLdapsCheckbox;
    private Text ldapsPortText;
    private Text kerberosPortText;
    private Button enableNtpCheckbox;
    private Text ntpPortText;
    private Button enableDnsCheckbox;
    private Text dnsPortText;
    private Text changePasswordPortText;


    /**
     * Creates a new instance of GeneralPage.
     *
     * @param editor
     *      the associated editor
     */
    public GeneralPage( FormEditor editor )
    {
        super( editor, ID, TITLE );
    }


    /**
     * {@inheritDoc}
     */
    protected void createFormContent( IManagedForm managedForm )
    {
        PlatformUI.getWorkbench().getHelpSystem().setHelp( getPartControl(),
            ApacheDSConfigurationPluginConstants.PLUGIN_ID + "." + "configuration_editor_152" ); //$NON-NLS-1$ //$NON-NLS-2$

        ScrolledForm form = managedForm.getForm();
        form.setText( "General" );

        Composite parent = form.getBody();
        TableWrapLayout twl = new TableWrapLayout();
        twl.numColumns = 2;
        parent.setLayout( twl );
        FormToolkit toolkit = managedForm.getToolkit();

        Composite composite = toolkit.createComposite( parent );
        composite.setLayout( new GridLayout() );
        TableWrapData compositeTableWrapData = new TableWrapData( TableWrapData.FILL, TableWrapData.TOP, 1, 2 );
        compositeTableWrapData.grabHorizontal = true;
        composite.setLayoutData( compositeTableWrapData );

        Composite leftComposite = toolkit.createComposite( parent );
        leftComposite.setLayout( new GridLayout() );
        TableWrapData leftCompositeTableWrapData = new TableWrapData( TableWrapData.FILL, TableWrapData.TOP );
        leftCompositeTableWrapData.grabHorizontal = true;
        leftComposite.setLayoutData( leftCompositeTableWrapData );

        Composite rightComposite = toolkit.createComposite( parent );
        rightComposite.setLayout( new GridLayout() );
        TableWrapData rightCompositeTableWrapData = new TableWrapData( TableWrapData.FILL, TableWrapData.TOP );
        rightCompositeTableWrapData.grabHorizontal = true;
        rightComposite.setLayoutData( rightCompositeTableWrapData );

        createProtocolsSection( composite, toolkit );
        createLimitsSection( leftComposite, toolkit );
        createOptionsSection( rightComposite, toolkit );

        initFromInput();
        addListeners();
    }


    /**
     * Creates the Protocols Section
     *
     * @param parent
     *      the parent composite
     * @param toolkit
     *      the toolkit to use
     */
    private void createProtocolsSection( Composite parent, FormToolkit toolkit )
    {
        // Creation of the section
        Section section = toolkit.createSection( parent, Section.TITLE_BAR );
        section.setText( "Protocols" );
        section.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        Composite client = toolkit.createComposite( section );
        toolkit.paintBordersFor( client );
        client.setLayout( new GridLayout( 3, true ) );
        section.setClient( client );

        // LDAP
        Composite ldapProtocolComposite = createProtocolComposite( toolkit, client );
        enableLdapCheckbox = toolkit.createButton( ldapProtocolComposite, "Enable LDAP", SWT.CHECK );
        enableLdapCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false, 3, 1 ) );
        toolkit.createLabel( ldapProtocolComposite, "    " ); //$NON-NLS-1$
        toolkit.createLabel( ldapProtocolComposite, "Port:" );
        ldapPortText = createPortText( toolkit, ldapProtocolComposite );

        // LDAPS
        Composite ldapsProtocolComposite = createProtocolComposite( toolkit, client );
        enableLdapsCheckbox = toolkit.createButton( ldapsProtocolComposite, "Enable LDAPS", SWT.CHECK );
        enableLdapsCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false, 3, 1 ) );
        toolkit.createLabel( ldapsProtocolComposite, "    " ); //$NON-NLS-1$
        toolkit.createLabel( ldapsProtocolComposite, "Port:" );
        ldapsPortText = createPortText( toolkit, ldapsProtocolComposite );

        // Kerberos
        Composite kerberosProtocolComposite = createProtocolComposite( toolkit, client );
        enableKerberosCheckbox = toolkit.createButton( kerberosProtocolComposite, "Enable Kerberos", SWT.CHECK );
        enableKerberosCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false, 3, 1 ) );
        toolkit.createLabel( kerberosProtocolComposite, "    " ); //$NON-NLS-1$
        toolkit.createLabel( kerberosProtocolComposite, "Port:" );
        kerberosPortText = createPortText( toolkit, kerberosProtocolComposite );

        // NTP
        Composite ntpProtocolComposite = createProtocolComposite( toolkit, client );
        enableNtpCheckbox = toolkit.createButton( ntpProtocolComposite, "Enable NTP", SWT.CHECK );
        enableNtpCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false, 3, 1 ) );
        toolkit.createLabel( ntpProtocolComposite, "    " ); //$NON-NLS-1$
        toolkit.createLabel( ntpProtocolComposite, "Port:" );
        ntpPortText = createPortText( toolkit, ntpProtocolComposite );

        // DNS
        Composite dnsProtocolComposite = createProtocolComposite( toolkit, client );
        enableDnsCheckbox = toolkit.createButton( dnsProtocolComposite, "Enable DNS", SWT.CHECK );
        enableDnsCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false, 3, 1 ) );
        toolkit.createLabel( dnsProtocolComposite, "    " ); //$NON-NLS-1$
        toolkit.createLabel( dnsProtocolComposite, "Port:" );
        dnsPortText = createPortText( toolkit, dnsProtocolComposite );

        // Change Password
        Composite changePasswordProtocolComposite = createProtocolComposite( toolkit, client );
        enableChangePasswordCheckbox = toolkit.createButton( changePasswordProtocolComposite, "Enable Change Password",
            SWT.CHECK );
        enableChangePasswordCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false, 3, 1 ) );
        toolkit.createLabel( changePasswordProtocolComposite, "    " ); //$NON-NLS-1$
        toolkit.createLabel( changePasswordProtocolComposite, "Port:" );
        changePasswordPortText = createPortText( toolkit, changePasswordProtocolComposite );
    }


    /**
     * Creates a Protocol Composite : a Composite composed of a GridLayout with
     * 3 columns and marginHeight and marginWidth set to 0.
     *
     * @param toolkit
     *      the toolkit
     * @param parent
     *      the parent
     * @return
     *      a Protocol Composite
     */
    private Composite createProtocolComposite( FormToolkit toolkit, Composite parent )
    {
        Composite protocolComposite = toolkit.createComposite( parent );
        GridLayout protocolGridLayout = new GridLayout( 3, false );
        protocolGridLayout.marginHeight = protocolGridLayout.marginWidth = 0;
        toolkit.paintBordersFor( protocolComposite );
        protocolComposite.setLayout( protocolGridLayout );

        return protocolComposite;
    }


    /**
     * Creates a Text that can be used to enter a port number.
     *
     * @param toolkit
     *      the toolkit
     * @param parent
     *      the parent
     * @return
     *      a Text that can be used to enter a port number
     */
    private Text createPortText( FormToolkit toolkit, Composite parent )
    {
        Text portText = toolkit.createText( parent, "" ); //$NON-NLS-1$
        GridData gd = new GridData( SWT.NONE, SWT.NONE, false, false );
        gd.widthHint = 42;
        portText.setLayoutData( gd );
        portText.addVerifyListener( new VerifyListener()
        {
            public void verifyText( VerifyEvent e )
            {
                if ( !e.text.matches( "[0-9]*" ) ) //$NON-NLS-1$
                {
                    e.doit = false;
                }
            }
        } );
        portText.setTextLimit( 5 );

        return portText;
    }


    /**
     * Creates the Limits Section
     *
     * @param parent
     *      the parent composite
     * @param toolkit
     *      the toolkit to use
     */
    private void createLimitsSection( Composite parent, FormToolkit toolkit )
    {
        // Creation of the section
        Section section = toolkit.createSection( parent, Section.TITLE_BAR );
        section.setText( "Limits" );
        section.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        Composite client = toolkit.createComposite( section );
        toolkit.paintBordersFor( client );
        GridLayout glayout = new GridLayout( 2, false );
        client.setLayout( glayout );
        section.setClient( client );

        // Max. Time Limit
        toolkit.createLabel( client, "Max. Time Limit:" );
        maxTimeLimitText = toolkit.createText( client, "" ); //$NON-NLS-1$
        maxTimeLimitText.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        maxTimeLimitText.addVerifyListener( new VerifyListener()
        {
            public void verifyText( VerifyEvent e )
            {
                if ( !e.text.matches( "[0-9]*" ) ) //$NON-NLS-1$
                {
                    e.doit = false;
                }
            }
        } );

        // Max. Size Limit
        toolkit.createLabel( client, "Max. Size Limit:" );
        maxSizeLimitText = toolkit.createText( client, "" ); //$NON-NLS-1$
        maxSizeLimitText.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        maxSizeLimitText.addVerifyListener( new VerifyListener()
        {
            public void verifyText( VerifyEvent e )
            {
                if ( !e.text.matches( "[0-9]*" ) ) //$NON-NLS-1$
                {
                    e.doit = false;
                }
            }
        } );

        // Synchronization Period
        toolkit.createLabel( client, "Synchronization Period:" );
        synchPeriodText = toolkit.createText( client, "" ); //$NON-NLS-1$
        synchPeriodText.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        synchPeriodText.addVerifyListener( new VerifyListener()
        {
            public void verifyText( VerifyEvent e )
            {
                if ( !e.text.matches( "[0-9]*" ) ) //$NON-NLS-1$
                {
                    e.doit = false;
                }
            }
        } );

        // Max. Threads
        toolkit.createLabel( client, "Max. Threads:" );
        maxThreadsText = toolkit.createText( client, "" ); //$NON-NLS-1$
        maxThreadsText.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        maxThreadsText.addVerifyListener( new VerifyListener()
        {
            public void verifyText( VerifyEvent e )
            {
                if ( !e.text.matches( "[0-9]*" ) ) //$NON-NLS-1$
                {
                    e.doit = false;
                }
            }
        } );
    }


    /**
     * Creates the Options Section
     *
     * @param parent
     *      the parent composite
     * @param toolkit
     *      the toolkit to use
     */
    private void createOptionsSection( Composite parent, FormToolkit toolkit )
    {
        // Creation of the section
        Section section = toolkit.createSection( parent, Section.TITLE_BAR );
        section.setText( "Options" );
        section.setLayoutData( new GridData( SWT.FILL, SWT.NONE, true, false ) );
        Composite client = toolkit.createComposite( section );
        toolkit.paintBordersFor( client );
        client.setLayout( new GridLayout() );
        section.setClient( client );

        // Allow Anonymous Access
        allowAnonymousAccessCheckbox = toolkit.createButton( client, "Allow Anonymous Access", SWT.CHECK );
        allowAnonymousAccessCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false ) );

        // Enable Access Control
        enableAccesControlCheckbox = toolkit.createButton( client, "Enable Access Control", SWT.CHECK );
        enableAccesControlCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false ) );

        // Denormalize Operational Attributes
        denormalizeOpAttrCheckbox = toolkit.createButton( client, "Denormalize Operational Attributes", SWT.CHECK );
        denormalizeOpAttrCheckbox.setLayoutData( new GridData( SWT.NONE, SWT.NONE, true, false ) );
    }


    /**
     * Initializes the page with the Editor input.
     */
    private void initFromInput()
    {
        ServerConfigurationV152 configuration = ( ServerConfigurationV152 ) ( ( ServerConfigurationEditor ) getEditor() )
            .getServerConfiguration();

        // LDAP Protocol
        enableLdapCheckbox.setSelection( configuration.isEnableLdap() );
        ldapPortText.setEnabled( enableLdapCheckbox.getSelection() );
        ldapPortText.setText( "" + configuration.getLdapPort() ); //$NON-NLS-1$

        // LDAPS Protocol
        enableLdapsCheckbox.setSelection( configuration.isEnableLdaps() );
        ldapsPortText.setEnabled( enableLdapsCheckbox.getSelection() );
        ldapsPortText.setText( "" + configuration.getLdapsPort() ); //$NON-NLS-1$

        // Kerberos Protocol
        enableKerberosCheckbox.setSelection( configuration.isEnableKerberos() );
        kerberosPortText.setEnabled( enableKerberosCheckbox.getSelection() );
        kerberosPortText.setText( "" + configuration.getKerberosPort() ); //$NON-NLS-1$

        // NTP Protocol
        enableNtpCheckbox.setSelection( configuration.isEnableNtp() );
        ntpPortText.setEnabled( enableNtpCheckbox.getSelection() );
        ntpPortText.setText( "" + configuration.getNtpPort() ); //$NON-NLS-1$

        // DNS Protocol
        enableDnsCheckbox.setSelection( configuration.isEnableDns() );
        dnsPortText.setEnabled( enableDnsCheckbox.getSelection() );
        dnsPortText.setText( "" + configuration.getDnsPort() ); //$NON-NLS-1$

        // Change Password Protocol
        enableChangePasswordCheckbox.setSelection( configuration.isEnableChangePassword() );
        changePasswordPortText.setEnabled( enableChangePasswordCheckbox.getSelection() );
        changePasswordPortText.setText( "" + configuration.getChangePasswordPort() ); //$NON-NLS-1$

        // Max Time Limit
        maxTimeLimitText.setText( "" + configuration.getMaxTimeLimit() ); //$NON-NLS-1$

        // Max Size Limit
        maxSizeLimitText.setText( "" + configuration.getMaxSizeLimit() ); //$NON-NLS-1$

        // Synchronization Period
        synchPeriodText.setText( "" + configuration.getSynchronizationPeriod() ); //$NON-NLS-1$

        // Max Threads
        maxThreadsText.setText( "" + configuration.getMaxThreads() ); //$NON-NLS-1$

        // Allow Anonymous Access
        allowAnonymousAccessCheckbox.setSelection( configuration.isAllowAnonymousAccess() );

        // Enable Access Control
        enableAccesControlCheckbox.setSelection( configuration.isEnableAccessControl() );

        // Denormalize Op Attr
        denormalizeOpAttrCheckbox.setSelection( configuration.isDenormalizeOpAttr() );
    }


    /**
     * Add listeners to UI fields.
     */
    private void addListeners()
    {
        // The Modify Listener
        ModifyListener modifyListener = new ModifyListener()
        {
            public void modifyText( ModifyEvent e )
            {
                setEditorDirty();
            }
        };

        //  The Selection Listener
        SelectionListener selectionListener = new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                setEditorDirty();
            }
        };

        enableLdapCheckbox.addSelectionListener( selectionListener );
        enableLdapCheckbox.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                ldapPortText.setEnabled( enableLdapCheckbox.getSelection() );
            }
        } );
        ldapPortText.addModifyListener( modifyListener );
        enableLdapsCheckbox.addSelectionListener( selectionListener );
        enableLdapsCheckbox.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                ldapsPortText.setEnabled( enableLdapsCheckbox.getSelection() );
            }
        } );
        ldapsPortText.addModifyListener( modifyListener );
        enableKerberosCheckbox.addSelectionListener( selectionListener );
        enableKerberosCheckbox.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                kerberosPortText.setEnabled( enableKerberosCheckbox.getSelection() );
            }
        } );
        kerberosPortText.addModifyListener( modifyListener );
        enableNtpCheckbox.addSelectionListener( selectionListener );
        enableNtpCheckbox.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                ntpPortText.setEnabled( enableNtpCheckbox.getSelection() );
            }
        } );
        ntpPortText.addModifyListener( modifyListener );
        enableDnsCheckbox.addSelectionListener( selectionListener );
        enableDnsCheckbox.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                dnsPortText.setEnabled( enableDnsCheckbox.getSelection() );
            }
        } );
        dnsPortText.addModifyListener( modifyListener );
        enableChangePasswordCheckbox.addSelectionListener( selectionListener );
        enableChangePasswordCheckbox.addSelectionListener( new SelectionAdapter()
        {
            public void widgetSelected( SelectionEvent e )
            {
                changePasswordPortText.setEnabled( enableChangePasswordCheckbox.getSelection() );
            }
        } );
        changePasswordPortText.addModifyListener( modifyListener );

        maxTimeLimitText.addModifyListener( modifyListener );
        maxSizeLimitText.addModifyListener( modifyListener );
        synchPeriodText.addModifyListener( modifyListener );
        maxThreadsText.addModifyListener( modifyListener );

        allowAnonymousAccessCheckbox.addSelectionListener( selectionListener );
        enableAccesControlCheckbox.addSelectionListener( selectionListener );
        denormalizeOpAttrCheckbox.addSelectionListener( selectionListener );
    }


    /**
     * Sets the Editor as dirty.
     */
    private void setEditorDirty()
    {
        ( ( ServerConfigurationEditor ) getEditor() ).setDirty( true );
    }


    /**
     * {@inheritDoc}
     */
    public void doSave( IProgressMonitor monitor )
    {
        ServerConfigurationV152 configuration = ( ServerConfigurationV152 ) ( ( ServerConfigurationEditor ) getEditor() )
            .getServerConfiguration();

        configuration.setEnableLdap( enableLdapCheckbox.getSelection() );
        configuration.setLdapPort( Integer.parseInt( ldapPortText.getText() ) );
        configuration.setEnableLdaps( enableLdapsCheckbox.getSelection() );
        configuration.setLdapsPort( Integer.parseInt( ldapsPortText.getText() ) );
        configuration.setEnableKerberos( enableKerberosCheckbox.getSelection() );
        configuration.setKerberosPort( Integer.parseInt( kerberosPortText.getText() ) );
        configuration.setEnableNtp( enableNtpCheckbox.getSelection() );
        configuration.setNtpPort( Integer.parseInt( ntpPortText.getText() ) );
        configuration.setEnableDns( enableDnsCheckbox.getSelection() );
        configuration.setDnsPort( Integer.parseInt( dnsPortText.getText() ) );
        configuration.setEnableChangePassword( enableChangePasswordCheckbox.getSelection() );
        configuration.setChangePasswordPort( Integer.parseInt( changePasswordPortText.getText() ) );

        configuration.setMaxTimeLimit( Integer.parseInt( maxTimeLimitText.getText() ) );
        configuration.setMaxSizeLimit( Integer.parseInt( maxSizeLimitText.getText() ) );
        configuration.setSynchronizationPeriod( Long.parseLong( synchPeriodText.getText() ) );
        configuration.setMaxThreads( Integer.parseInt( maxThreadsText.getText() ) );

        configuration.setAllowAnonymousAccess( allowAnonymousAccessCheckbox.getSelection() );
        configuration.setEnableAccessControl( enableAccesControlCheckbox.getSelection() );
        configuration.setDenormalizeOpAttr( denormalizeOpAttrCheckbox.getSelection() );
    }
}
